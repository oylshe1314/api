package com.sk.op.api.server.dto;

import com.sk.op.api.server.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "角色选择列表")
public class RoleSelectDto {

    @Schema(name = "id", title = "ID")
    private final Long id;

    @Schema(name = "name", title = "名称")
    private final String name;

    public RoleSelectDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
