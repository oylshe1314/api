package com.sk.op.api.server.controller;

import com.sk.op.api.common.dto.ResponseDto;
import com.sk.op.api.common.validation.Integers;
import com.sk.op.api.server.auth.AdminDetails;
import com.sk.op.api.server.dto.MenuSelectDto;
import com.sk.op.api.server.dto.MenuSortableDto;
import com.sk.op.api.server.dto.RoleMenuDto;
import com.sk.op.api.server.dto.RoleSelectDto;
import com.sk.op.api.server.entity.Menu;
import com.sk.op.api.server.entity.base.MenuType;
import com.sk.op.api.server.entity.base.State;
import com.sk.op.api.server.service.MenuService;
import com.sk.op.api.server.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/common")
@Tag(name = "common", description = "公共接口")
public class CommonController {

    private final RoleService roleService;
    private final MenuService menuService;

    public CommonController(RoleService roleService, MenuService menuService) {
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @Operation(summary = "角色菜单")
    @RequestMapping(value = "/role/menus", method = RequestMethod.GET)
    public ResponseDto<List<RoleMenuDto>> roleMenus(Authentication authentication) {
        AdminDetails adminDetails = (AdminDetails) authentication.getPrincipal();
        List<Menu> menus = menuService.getAll(adminDetails.getRole().getMenuIds());
        Map<String, RoleMenuDto> roleMenuMap = menus.stream()
                .filter(menu -> menu.getType() == MenuType.dir.value() || menu.getType() == MenuType.menu.value())
                .map(RoleMenuDto::new)
                .collect(Collectors.toMap(RoleMenuDto::getId, roleMenuDto -> roleMenuDto));

        List<RoleMenuDto> roleMenus = new ArrayList<>();
        roleMenuMap.forEach((menuId, menu) -> {
            if (menu.getParentId() == null) {
                roleMenus.add(menu);
            } else {
                RoleMenuDto parent = roleMenuMap.get(menu.getParentId());
                if (parent != null) {
                    parent.getSubMenus().add(menu);
                }
            }
        });

        MenuSortableDto.sort(roleMenus);

        return ResponseDto.succeed(roleMenus);
    }

    @Operation(summary = "角色选择列表")
    @RequestMapping(value = "/select/roles", method = RequestMethod.GET)
    public ResponseDto<List<RoleSelectDto>> selectRoles() {
        return ResponseDto.succeed(roleService.getAll(State.enable).stream().map(RoleSelectDto::new).toList());
    }

    @Operation(summary = "菜单选择列表", parameters = {
            @Parameter(name = "type", required = true, description = "菜单类型")
    })
    @RequestMapping(value = "/select/menus", method = RequestMethod.GET)
    public ResponseDto<List<MenuSelectDto>> selectMenus(@NotNull @Integers({1, 2}) Integer type) {
        List<Menu> menus = menuService.getAll(type);

        menus.sort((m1, m2) -> {
            if (type == 1) {
                return m1.getSortBy().compareTo(m2.getSortBy());
            } else {
                if (m1.getParentId().equals(m2.getParentId())) {
                    return m1.getSortBy().compareTo(m2.getSortBy());
                } else {
                    return m1.getParent().getSortBy().compareTo(m2.getParent().getSortBy());
                }
            }
        });

        return ResponseDto.succeed(menus.stream().map(MenuSelectDto::new).toList());
    }
}
