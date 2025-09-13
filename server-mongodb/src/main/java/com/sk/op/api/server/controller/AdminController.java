package com.sk.op.api.server.controller;

import com.sk.op.api.common.dto.PageRequestDto;
import com.sk.op.api.common.dto.PageResultDto;
import com.sk.op.api.common.dto.ResponseDto;
import com.sk.op.api.server.auth.AdminDetails;
import com.sk.op.api.server.dto.*;
import com.sk.op.api.server.entity.Admin;
import com.sk.op.api.server.entity.Menu;
import com.sk.op.api.server.entity.Role;
import com.sk.op.api.server.entity.base.MenuType;
import com.sk.op.api.server.entity.base.State;
import com.sk.op.api.server.service.AdminService;
import com.sk.op.api.server.service.MenuService;
import com.sk.op.api.server.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/admin")
@Tag(name = "admin", description = "管理员相关接口")
public class AdminController {

    private final RoleService roleService;
    private final MenuService menuService;
    private final AdminService adminService;

    public AdminController(RoleService roleService, MenuService menuService, AdminService adminService) {
        this.roleService = roleService;
        this.menuService = menuService;
        this.adminService = adminService;
    }

    @Operation(summary = "角色查询", parameters = {
            @Parameter(name = "pageNo", required = true, description = "页码(从1开始)"),
            @Parameter(name = "pageSize", required = true, description = "单页数目(10-50)"),
            @Parameter(name = "name", description = "查询参数 - 名称", schema = @Schema(implementation = String.class)),
    })
    @RequestMapping(value = "/role/query", method = RequestMethod.GET)
    public ResponseDto<PageResultDto<RoleDto>> roleQuery(@NotNull @Positive Integer pageNo, @NotNull @Range(min = 10, max = 50) Integer pageSize, @Parameter(hidden = true) @Valid RoleQueryDto dto) {
        if (dto != null && dto.getName() == null) {
            dto = null;
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Role> page = roleService.query(pageable, dto);
        return ResponseDto.succeed(new PageResultDto<>(pageable, page, RoleDto::new));
    }

    @Operation(summary = "角色查询")
    @RequestMapping(value = "/role/query", method = RequestMethod.POST)
    public ResponseDto<PageResultDto<RoleDto>> roleQuery(@RequestBody @Valid PageRequestDto<RoleQueryDto> dto) {
        return roleQuery(dto.getPageNo(), dto.getPageSize(), dto.getQuery());
    }

    @Operation(summary = "角色添加")
    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    public ResponseDto<RoleDto> roleAdd(@RequestBody @Valid RoleAddDto dto, Authentication authentication) {
        return ResponseDto.succeed(new RoleDto(roleService.add(dto, (AdminDetails) authentication.getPrincipal())));
    }

    @Operation(summary = "角色修改")
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    public ResponseDto<RoleDto> roleUpdate(@RequestBody @Valid RoleUpdateDto dto, Authentication authentication) {
        return ResponseDto.succeed(new RoleDto(roleService.update(dto, (AdminDetails) authentication.getPrincipal())));
    }

    @Operation(summary = "角色删除")
    @RequestMapping(value = "/role/delete", method = RequestMethod.POST)
    public ResponseDto<?> roleDelete(@RequestBody @Valid DeleteDto dto) throws Exception {
        roleService.delete(dto);
        return ResponseDto.succeed();
    }

    @Operation(summary = "角色状态修改")
    @RequestMapping(value = "/role/change/state", method = RequestMethod.POST)
    public ResponseDto<?> roleChangeState(@RequestBody @Valid ChangeStateDto dto, Authentication authentication) throws Exception {
        roleService.changeState(dto, (AdminDetails) authentication.getPrincipal());
        return ResponseDto.succeed();
    }

    @Operation(summary = "角色菜单查询", parameters = {
            @Parameter(name = "roleId", required = true, description = "角色ID")
    })
    @RequestMapping(value = "/role/menu/query", method = RequestMethod.GET)
    public ResponseDto<List<MenuTreeDto>> roleAuthorities(@NotBlank String roleId) {
        Role role = roleService.get(new ObjectId(roleId));
        List<Menu> roleMenus = menuService.getAll(role.getMenuIds());
        Map<ObjectId, Menu> roleMenuMap = roleMenus.stream().collect(Collectors.toMap(Menu::getId, menu -> menu));

        List<Menu> menus = menuService.getAll(State.enable);
        Map<String, MenuTreeDto> treeMap = menus.stream().map(menu -> new MenuTreeDto(menu, roleMenuMap.containsKey(menu.getId()))).collect(Collectors.toMap(MenuTreeDto::getId, tree -> tree));

        List<MenuTreeDto> treeMenus = new ArrayList<>();
        treeMap.forEach((id, tree) -> {
            if (tree.getParentId() == null) {
                treeMenus.add(tree);
            } else {
                MenuTreeDto parent = treeMap.get(tree.getParentId());
                if (parent != null) {
                    parent.getSubMenus().add(tree);
                }
            }
        });

        MenuSortableDto.sort(treeMenus);

        return ResponseDto.succeed(treeMenus);
    }

    @Operation(summary = "角色菜单设置")
    @RequestMapping(value = "/role/menu/set", method = RequestMethod.POST)
    public ResponseDto<?> setRoleMenus(@RequestBody @Valid RoleSetMenusDto dto) {
        roleService.setAuthorities(dto);
        return ResponseDto.succeed();
    }

    @Operation(summary = "管理员查询", parameters = {
            @Parameter(name = "pageNo", required = true, description = "页码(从1开始)"),
            @Parameter(name = "pageSize", required = true, description = "单页数目(10-50)"),
            @Parameter(name = "roleId", description = "查询参数 - 角色ID", schema = @Schema(implementation = Long.class)),
            @Parameter(name = "username", description = "查询参数 - 用户名", schema = @Schema(implementation = String.class)),
    })
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseDto<PageResultDto<AdminDto>> query(@NotNull @Positive Integer pageNo, @NotNull @Range(min = 10, max = 50) Integer pageSize, @Parameter(hidden = true) @Valid AdminQueryDto dto) {
        if (dto != null && dto.getRoleId() == null && dto.getUsername() == null) {
            dto = null;
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Admin> page = adminService.query(pageable, dto);
        return ResponseDto.succeed(new PageResultDto<>(pageable, page, AdminDto::new));
    }

    @Operation(summary = "管理员查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseDto<PageResultDto<AdminDto>> query(@RequestBody @Valid PageRequestDto<AdminQueryDto> dto) {
        return query(dto.getPageNo(), dto.getPageSize(), dto.getQuery());
    }

    @Operation(summary = "管理员添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseDto<AdminDto> add(@RequestBody @Valid AdminAddDto dto, Authentication authentication) {
        return ResponseDto.succeed(new AdminDto(adminService.add(dto, (AdminDetails) authentication.getPrincipal())));
    }

    @Operation(summary = "管理员修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseDto<AdminDto> update(@RequestBody @Valid AdminUpdateDto dto, Authentication authentication) {
        return ResponseDto.succeed(new AdminDto(adminService.update(dto, (AdminDetails) authentication.getPrincipal())));
    }

    @Operation(summary = "管理员删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseDto<?> delete(@RequestBody @Valid DeleteDto dto) throws Exception {
        adminService.delete(dto);
        return ResponseDto.succeed();
    }

    @Operation(summary = "管理员状态修改")
    @RequestMapping(value = "/change/state", method = RequestMethod.POST)
    public ResponseDto<?> changeState(@RequestBody @Valid ChangeStateDto dto, Authentication authentication) throws Exception {
        adminService.changeState(dto, (AdminDetails) authentication.getPrincipal());
        return ResponseDto.succeed();
    }
}
