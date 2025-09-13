package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "登录信息")
public class LoginDto {

    @NotBlank
    @Schema(description = "用户名")
    private final String username;

    @NotBlank
    @Schema(description = "密码")
    private final String password;

    @JsonCreator
    public LoginDto(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
