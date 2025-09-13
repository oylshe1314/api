package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "角色添加")
public class RoleAddDto {

    @NotBlank
    @Schema(title = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String name;

    @Schema(title = "备注")
    private final String remark;

    public RoleAddDto(@JsonProperty("name") String name,
                      @JsonProperty("remark") String remark) {
        this.name = name;
        this.remark = remark;
    }
}
