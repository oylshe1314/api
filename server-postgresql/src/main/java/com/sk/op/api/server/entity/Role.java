package com.sk.op.api.server.entity;

import com.sk.op.api.server.entity.base.EntityStateful;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Role extends EntityStateful {

    private String name;
}
