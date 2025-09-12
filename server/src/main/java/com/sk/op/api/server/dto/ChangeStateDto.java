package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.op.api.common.validation.Integers;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(title = "通用状态修改")
public class ChangeStateDto {

    @NotEmpty
    @Schema(title = "ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private final List<String> ids;

    @Integers({0, 1})
    @Schema(title = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Integer state;

    public ChangeStateDto(@JsonProperty("ids") List<String> ids, @JsonProperty("state") Integer state) {
        this.ids = ids;
        this.state = state;
    }
}
