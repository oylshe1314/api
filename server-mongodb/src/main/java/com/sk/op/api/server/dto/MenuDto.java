package com.sk.op.api.server.dto;

import com.sk.op.api.server.dto.base.DtoStateful;
import com.sk.op.api.server.entity.Menu;
import com.sk.op.api.server.entity.base.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "菜单")
public class MenuDto extends DtoStateful {

    @Schema(title = "类型", description = "1.目录, 2.菜单, 3.接口")
    private final Integer type;

    @Schema(title = "类型名称")
    private final String typeName;

    @Schema(title = "上级菜单ID")
    private final String parentId;

    @Schema(title = "上级菜单名称")
    private final String parentName;

    @Schema(title = "图标")
    private final String icon;

    @Schema(title = "名称")
    private final String name;

    @Schema(title = "路径")
    private final String path;

    @Schema(title = "排序值")
    private final Integer sortBy;

    public MenuDto(Menu menu) {
        super(menu.getId(), menu.getRemark(), menu.getUpdateBy(), menu.getUpdateTime(), menu.getState());
        if (menu.getParentId() == null) {
            this.parentId = "";
            this.parentName = "";
        } else {
            this.parentId = menu.getParent().getId().toHexString();
            this.parentName = menu.getParent().getName();
        }
        this.type = menu.getType();
        this.typeName = MenuType.desc(this.type);
        this.icon = menu.getIcon();
        this.name = menu.getName();
        this.path = menu.getPath();
        this.sortBy = menu.getSortBy();
    }
}
