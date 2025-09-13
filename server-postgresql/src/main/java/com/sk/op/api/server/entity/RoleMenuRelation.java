package com.sk.op.api.server.entity;


import com.sk.op.api.server.entity.base.EntityIdentity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "relationWithMenu", attributeNodes = {
                @NamedAttributeNode("menu")
        })
})
public class RoleMenuRelation extends EntityIdentity {

    private Long roleId;

    private Long menuId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId", insertable = false, updatable = false)
    private Menu menu;
}
