package com.sk.op.api.server.entity;

import com.sk.op.api.server.entity.base.EntityStateful;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
public class Role extends EntityStateful {

    private String name;

    private List<ObjectId> menuIds;
}
