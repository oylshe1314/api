package com.sk.op.api.server.dto;

import com.sk.op.api.server.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "菜单树")
public class MenuTreeDto extends MenuSortableDto<MenuTreeDto> {

    @Schema(title = "ID")
    private final String id;

    @Schema(title = "上级菜单ID")
    private final String parentId;

    @Schema(title = "名称")
    private final String name;

    @Schema(title = "是否选中")
    private final Boolean checked;

    public MenuTreeDto(Menu menu, Boolean checked) {
        super(menu.getSortBy());
        this.id = menu.getId().toHexString();
        this.parentId = menu.getParentId() == null ? null : menu.getParentId().toHexString();
        this.name = menu.getName();
        this.checked = checked;
    }
}
