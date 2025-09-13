package com.sk.op.api.server.controller;

import com.sk.op.api.common.dto.PageRequestDto;
import com.sk.op.api.common.dto.PageResultDto;
import com.sk.op.api.common.dto.ResponseDto;
import com.sk.op.api.server.auth.AdminDetails;
import com.sk.op.api.server.dto.*;
import com.sk.op.api.server.entity.Menu;
import com.sk.op.api.server.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

@Validated
@RestController
@RequestMapping("/menu")
@Tag(name = "menu", description = "菜单相关接口")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "菜单查询", parameters = {
            @Parameter(name = "pageNo", required = true, description = "页码(从1开始)"),
            @Parameter(name = "pageSize", required = true, description = "单页数目(10-50)"),
            @Parameter(name = "type", description = "查询参数 - 类型(1.目录, 2.菜单, 3.接口)", schema = @Schema(implementation = Integer.class)),
            @Parameter(name = "parentName", description = "查询参数 - 上级菜单名称", schema = @Schema(implementation = String.class)),
            @Parameter(name = "name", description = "查询参数 - 名称", schema = @Schema(implementation = String.class)),
            @Parameter(name = "path", description = "查询参数 - 路径", schema = @Schema(implementation = String.class)),
    })
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseDto<PageResultDto<MenuDto>> query(@NotNull @Positive Integer pageNo, @NotNull @Range(min = 10, max = 50) Integer pageSize, @Parameter(hidden = true) @Valid MenuQueryDto dto) {
        if (dto != null) {
            if (dto.getType() == null && dto.getParentName() == null && dto.getName() == null && dto.getPath() == null) {
                dto = null;
            }
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Menu> page = menuService.query(pageable, dto);
        return ResponseDto.succeed(new PageResultDto<>(pageable, page, MenuDto::new));
    }

    @Operation(summary = "菜单查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseDto<PageResultDto<MenuDto>> query(@RequestBody @Valid PageRequestDto<MenuQueryDto> dto) {
        return query(dto.getPageNo(), dto.getPageSize(), dto.getQuery());
    }

    @Operation(summary = "菜单添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseDto<MenuDto> add(@RequestBody @Valid MenuAddDto dto, Authentication authentication) {
        return ResponseDto.succeed(new MenuDto(menuService.add(dto, (AdminDetails) authentication.getPrincipal())));
    }

    @Operation(summary = "菜单修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseDto<MenuDto> update(@RequestBody @Valid MenuUpdateDto dto, Authentication authentication) {
        return ResponseDto.succeed(new MenuDto(menuService.update(dto, (AdminDetails) authentication.getPrincipal())));
    }

    @Operation(summary = "菜单删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseDto<?> delete(@RequestBody @Valid DeleteDto dto) {
        menuService.delete(dto);
        return ResponseDto.succeed();
    }

    @Operation(summary = "菜单状态修改")
    @RequestMapping(value = "/change/state", method = RequestMethod.POST)
    public ResponseDto<?> changeState(@RequestBody @Valid ChangeStateDto dto, Authentication authentication) {
        menuService.changeState(dto, (AdminDetails) authentication.getPrincipal());
        return ResponseDto.succeed();
    }
}
