package com.sk.op.api.server.dto.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
public class DtoOperation extends DtoIdentity {

    @Schema(title = "备注")
    private final String remark;

    @Schema(title = "操作员")
    private final String updateBy;

    @Schema(title = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime updateTime;

    protected DtoOperation(ObjectId id, String remark, String updateBy, LocalDateTime updateTime) {
        super(id);
        this.remark = remark;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }
}
