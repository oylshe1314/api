package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.op.api.common.validation.Integers;
import com.sk.op.api.common.validation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
@Schema(title = "菜单添加")
public class MenuAddDto {

    @NotNull
    @Integers({1, 2, 3})
    @Schema(title = "类型", description = "1.目录, 2.菜单, 3.接口", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Integer type;

    @NullOrNotBlank
    @Schema(title = "上级菜单ID", description = "类型为2,3时必填")
    private final Long parentId;

    @Schema(title = "图标")
    private final String icon;

    @NotBlank
    @Schema(title = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String name;

    @Schema(title = "路径", description = "类型为2,3时必填")
    private final String path;

    @NotNull
    @Positive
    @Schema(title = "排序值", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Integer sortBy;

    @Schema(title = "备注")
    private final String remark;

    public MenuAddDto(@JsonProperty("parentId") Long parentId,
                      @JsonProperty("type") Integer type,
                      @JsonProperty("icon") String icon,
                      @JsonProperty("name") String name,
                      @JsonProperty("path") String path,
                      @JsonProperty("sortBy") Integer sortBy,
                      @JsonProperty("remark") String remark) {
        this.parentId = parentId;
        this.type = type;
        this.icon = icon;
        this.name = name;
        this.path = path;
        this.sortBy = sortBy;
        this.remark = remark;
    }
}
