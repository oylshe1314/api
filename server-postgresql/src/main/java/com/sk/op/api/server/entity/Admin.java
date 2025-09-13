package com.sk.op.api.server.entity;

import com.sk.op.api.server.entity.base.EntityStateful;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "adminWithRole", attributeNodes = {
                @NamedAttributeNode("role")
        })
})
public class Admin extends EntityStateful {

    private Long roleId;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private String mobile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", insertable = false, updatable = false)
    private Role role;
}
