package com.sk.op.api.server.dto;

import com.sk.op.api.server.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "菜单树")
public class MenuTreeDto extends MenuSortableDto<MenuTreeDto> {

    @Schema(title = "ID")
    private final Long id;

    @Schema(title = "上级菜单ID")
    private final Long parentId;

    @Schema(title = "名称")
    private final String name;

    @Schema(title = "是否选中")
    private final Boolean checked;

    public MenuTreeDto(Menu menu, Boolean checked) {
        super(menu.getSortBy());
        this.id = menu.getId();
        this.parentId = menu.getParentId() == null ? 0L : menu.getParentId();
        this.name = menu.getName();
        this.checked = checked;
    }
}
