package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "管理员添加")
public class AdminAddDto {

    @NotBlank
    @Schema(title = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String roleId;

    @NotBlank
    @Schema(title = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String username;

    @NotBlank
    @Schema(title = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String password;

    @NotBlank
    @Schema(title = "昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String nickname;

    @Schema(title = "头像")
    private final String avatar;

    @Schema(title = "邮箱")
    private final String email;

    @Schema(title = "手机")
    private final String mobile;

    @Schema(title = "备注")
    private final String remark;

    public AdminAddDto(@JsonProperty("roleId") String roleId,
                       @JsonProperty("username") String username,
                       @JsonProperty("password") String password,
                       @JsonProperty("nickname") String nickname,
                       @JsonProperty("avatar") String avatar,
                       @JsonProperty("email") String email,
                       @JsonProperty("mobile") String mobile,
                       @JsonProperty("remark") String remark) {
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.email = email;
        this.mobile = mobile;
        this.remark = remark;
    }
}
