package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.op.api.common.validation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "管理员修改")
public class AdminUpdateDto {

    @NotBlank
    @Schema(title = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String id;

    @NullOrNotBlank
    @Schema(title = "角色ID")
    private final String roleId;

    @NullOrNotBlank
    @Schema(title = "用户名")
    private final String password;

    @NullOrNotBlank
    @Schema(title = "密码")
    private final String nickname;

    @Schema(title = "昵称")
    private final String avatar;

    @Schema(title = "头像")
    private final String email;

    @Schema(title = "邮箱")
    private final String mobile;

    @Schema(title = "手机")
    private final String remark;

    public AdminUpdateDto(@JsonProperty("id") String id,
                          @JsonProperty("roleId") String roleId,
                          @JsonProperty("password") String password,
                          @JsonProperty("nickname") String nickname,
                          @JsonProperty("avatar") String avatar,
                          @JsonProperty("email") String email,
                          @JsonProperty("mobile") String mobile,
                          @JsonProperty("remark") String remark) {
        this.id = id;
        this.roleId = roleId;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.email = email;
        this.mobile = mobile;
        this.remark = remark;
    }
}
