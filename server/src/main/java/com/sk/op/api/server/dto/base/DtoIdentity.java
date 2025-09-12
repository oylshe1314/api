package com.sk.op.api.server.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
public class DtoIdentity {

    @Schema(title = "ID")
    private final String id;

    protected DtoIdentity(ObjectId id) {
        this.id = id.toHexString();
    }
}
