package com.sk.op.api.server.dto;

import com.sk.op.api.server.auth.AdminDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "登录结果信息")
public class LoginResultDto {

    @Schema(description = "角色名称")
    private final String roleName;

    @Schema(description = "用户名")
    private final String username;

    @Schema(description = "昵称")
    private final String nickname;

    @Schema(description = "头像")
    private final String avatar;

    @Schema(description = "邮箱")
    private final String email;

    @Schema(description = "手机号")
    private final String mobile;

    public LoginResultDto(AdminDetails adminDetails) {
        this.roleName = adminDetails.getRoleName();
        this.username = adminDetails.getUsername();
        this.nickname = adminDetails.getNickname();
        this.avatar = adminDetails.getAvatar();
        this.email = adminDetails.getEmail();
        this.mobile = adminDetails.getMobile();
    }
}
