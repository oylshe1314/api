package com.sk.op.api.server.entity.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EntityOperation extends EntityIdentity {

    private String remark;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    public void initOperation(String remark, String createBy) {
        this.setRemark(remark == null ? "" : remark);
        this.setCreateBy(createBy);
        this.setCreateTime(LocalDateTime.now());
        this.setUpdateBy(this.getCreateBy());
        this.setUpdateTime(this.getCreateTime());
    }

    public void updateOperation(String remark, String updateBy) {
        if (remark != null) {
            this.setRemark(remark);
        }
        this.setUpdateBy(updateBy);
        this.setUpdateTime(LocalDateTime.now());
    }
}
