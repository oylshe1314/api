package com.sk.op.api.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(title = "角色菜单设置")
public class RoleSetMenusDto {

    @NotBlank
    @Schema(title = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Long roleId;

    @Schema(title = "菜单ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private final List<Long> menuIds;

    public RoleSetMenusDto(@JsonProperty("roleId") Long roleId,
                           @JsonProperty("menuIds") List<Long> menuIds) {
        this.roleId = roleId;
        this.menuIds = menuIds;
    }
}
