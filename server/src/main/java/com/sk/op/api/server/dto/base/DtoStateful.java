package com.sk.op.api.server.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
public class DtoStateful extends DtoOperation {

    @Schema(title = "状态", description = "0.禁用, 1.启用")
    private final Integer state;

    protected DtoStateful(ObjectId id, String remark, String updateBy, LocalDateTime updateTime, Integer state) {
        super(id, remark, updateBy, updateTime);
        this.state = state;
    }
}
