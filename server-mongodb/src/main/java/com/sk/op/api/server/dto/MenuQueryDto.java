package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.op.api.common.validation.Integers;
import com.sk.op.api.common.validation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "菜单查询")
public class MenuQueryDto {

    @Integers({1, 2, 3})
    @Schema(name = "type", title = "类型", description = "1.目录, 2.菜单, 3.接口")
    private final Integer type;

    @NullOrNotBlank
    @Schema(name = "parentName", title = "上级菜单名称")
    private final String parentName;

    @NullOrNotBlank
    @Schema(name = "name", title = "名称")
    private final String name;

    @NullOrNotBlank
    @Schema(name = "path", title = "路径")
    private final String path;

    public MenuQueryDto(@JsonProperty("type") Integer type,
                        @JsonProperty("parentName") String parentName,
                        @JsonProperty("name") String name,
                        @JsonProperty("path") String path) {
        this.type = type;
        this.parentName = parentName;
        this.name = name;
        this.path = path;
    }
}
