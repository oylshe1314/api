package com.sk.op.api.server.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class EntityIdentity {

    @Id
    private ObjectId id;
}
