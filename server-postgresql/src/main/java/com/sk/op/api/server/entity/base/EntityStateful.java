package com.sk.op.api.server.entity.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class EntityStateful extends EntityOperation {

    private Integer state;
}
