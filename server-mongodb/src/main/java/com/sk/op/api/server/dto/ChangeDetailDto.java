package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "详细信息修改")
public class ChangeDetailDto {

    @NotBlank
    @Schema(name = "nickname", title = "昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String nickname;

    @Schema(name = "avatar", title = "头像")
    private final String avatar;

    @Schema(name = "email", title = "邮箱")
    private final String email;

    @Schema(name = "mobile", title = "手机")
    private final String mobile;

    public ChangeDetailDto(@JsonProperty("nickname") String nickname,
                           @JsonProperty("avatar") String avatar,
                           @JsonProperty("email") String email,
                           @JsonProperty("mobile") String mobile) {
        this.nickname = nickname;
        this.avatar = avatar == null ? "" : avatar;
        this.email = email == null ? "" : email;
        this.mobile = mobile == null ? "" : mobile;
    }
}
