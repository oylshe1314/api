package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.op.api.common.validation.Integers;
import com.sk.op.api.common.validation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
@Schema(title = "菜单修改")
public class MenuUpdateDto {

    @NotBlank
    @Schema(title = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String id;

    @Integers({1, 2, 3})
    @Schema(title = "类型", description = "1.目录, 2.菜单, 3.接口")
    private final Integer type;

    @NullOrNotBlank
    @Schema(title = "上级菜单ID")
    private final String parentId;

    @Schema(title = "图标")
    private final String icon;

    @NullOrNotBlank
    @Schema(title = "名称")
    private final String name;

    @Schema(title = "路径")
    private final String path;

    @Positive
    @Schema(title = "排序值")
    private final Integer sortBy;

    @Schema(title = "备注")
    private final String remark;

    public MenuUpdateDto(@JsonProperty("id") String id,
                         @JsonProperty("parentId") String parentId,
                         @JsonProperty("type") Integer type,
                         @JsonProperty("icon") String icon,
                         @JsonProperty("name") String name,
                         @JsonProperty("path") String path,
                         @JsonProperty("sortBy") Integer sortBy,
                         @JsonProperty("remark") String remark) {
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.icon = icon;
        this.name = name;
        this.path = path;
        this.sortBy = sortBy;
        this.remark = remark;
    }
}
