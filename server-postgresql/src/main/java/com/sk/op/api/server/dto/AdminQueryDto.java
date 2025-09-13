package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.op.api.common.validation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
@Schema(title = "管理员查询")
public class AdminQueryDto {

    @Positive
    @Schema(title = "角色ID")
    private final Long roleId;

    @NullOrNotBlank
    @Schema(title = "用户名")
    private final String username;

    public AdminQueryDto(@JsonProperty("roleId") Long roleId,
                         @JsonProperty("username") String username) {
        this.roleId = roleId;
        this.username = username;
    }
}
