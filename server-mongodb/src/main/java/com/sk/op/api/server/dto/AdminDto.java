package com.sk.op.api.server.dto;

import com.sk.op.api.server.dto.base.DtoStateful;
import com.sk.op.api.server.entity.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "管理员")
public class AdminDto extends DtoStateful {

    @Schema(title = "角色ID")
    private final String roleId;

    @Schema(title = "用户名")
    private final String roleName;

    @Schema(title = "用户名")
    private final String username;

    @Schema(title = "密码")
    private final String nickname;

    @Schema(title = "昵称")
    private final String avatar;

    @Schema(title = "头像")
    private final String email;

    @Schema(title = "邮箱")
    private final String mobile;

    public AdminDto(Admin admin) {
        super(admin.getId(), admin.getRemark(), admin.getUpdateBy(), admin.getUpdateTime(), admin.getState());
        this.roleId = admin.getRoleId().toHexString();
        this.roleName = admin.getRole().getName();
        this.username = admin.getUsername();
        this.nickname = admin.getNickname();
        this.avatar = admin.getAvatar();
        this.email = admin.getEmail();
        this.mobile = admin.getMobile();
    }
}
