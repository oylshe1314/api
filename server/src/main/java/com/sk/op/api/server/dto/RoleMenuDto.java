package com.sk.op.api.server.dto;

import com.sk.op.api.server.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "角色菜单")
public class RoleMenuDto extends MenuSortableDto<RoleMenuDto> {

    @Schema(description = "ID")
    private final String id;

    @Schema(description = "父级ID")
    private final String parentId;

    @Schema(description = "类型: 1.目录, 2.菜单, 3.接口")
    private final Integer type;

    @Schema(description = "图标")
    private final String icon;

    @Schema(description = "名称")
    private final String name;

    @Schema(description = "路径")
    private final String path;

    public RoleMenuDto(Menu menu) {
        super(menu.getSortBy());
        this.id = menu.getId().toHexString();
        this.parentId = menu.getParentId() == null ? null : menu.getParentId().toHexString();
        this.type = menu.getType();
        this.icon = menu.getIcon();
        this.name = menu.getName();
        this.path = menu.getPath();
    }
}
