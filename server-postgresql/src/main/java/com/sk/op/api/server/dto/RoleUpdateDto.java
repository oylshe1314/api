package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.op.api.common.validation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "角色修改")
public class RoleUpdateDto {

    @NotBlank
    @Schema(title = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Long id;

    @NullOrNotBlank
    @Schema(title = "名称")
    private final String name;

    @Schema(title = "备注")
    private final String remark;

    public RoleUpdateDto(@JsonProperty("id") Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("remark") String remark) {
        this.id = id;
        this.name = name;
        this.remark = remark;
    }
}
