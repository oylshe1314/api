package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "密码修改")
public class ChangePasswordDto {

    @NotBlank
    @Schema(title = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String oldPassword;

    @NotBlank
    @Schema(title = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String newPassword;

    @JsonCreator
    public ChangePasswordDto(@JsonProperty("oldPassword") String oldPassword,
                             @JsonProperty("newPassword") String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
