package com.sk.op.api.server.entity;

import com.sk.op.api.server.entity.base.EntityStateful;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "menuWithParent", attributeNodes = {
                @NamedAttributeNode("parent")
        })
})
public class Menu extends EntityStateful {

    private Long parentId;

    private Integer type;

    private String icon;

    private String name;

    private String path;

    private Integer sortBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", insertable = false, updatable = false)
    private Menu parent;
}
