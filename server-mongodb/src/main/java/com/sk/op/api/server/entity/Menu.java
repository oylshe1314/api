package com.sk.op.api.server.entity;

import com.sk.op.api.server.entity.base.EntityStateful;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Menu extends EntityStateful {

    private ObjectId parentId;

    private Integer type;

    private String icon;

    private String name;

    private String path;

    private Integer sortBy;

    @DBRef
    private Menu parent;
}
