package com.sk.op.api.server.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DtoIdentity {

    @Schema(title = "ID")
    private final Long id;

    protected DtoIdentity(Long id) {
        this.id = id;
    }
}
