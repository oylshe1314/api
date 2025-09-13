package com.sk.op.api.server.dto;

import com.sk.op.api.server.dto.base.DtoStateful;
import com.sk.op.api.server.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "角色")
public class RoleDto extends DtoStateful {

    @Schema(title = "名称")
    private final String name;

    public RoleDto(Role role) {
        super(role.getId(), role.getRemark(), role.getUpdateBy(), role.getUpdateTime(), role.getState());
        this.name = role.getName();
    }
}
