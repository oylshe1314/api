package com.sk.op.api.server.auth;

import com.sk.op.api.server.entity.Admin;
import com.sk.op.api.server.entity.Role;
import com.sk.op.api.server.entity.base.State;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
public class AdminDetails implements UserDetails {

    private ObjectId id;

    private ObjectId roleId;

    private String roleName;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private String mobile;

    private Integer state;

    private Role role;

    private List<RoleGrantedAuthority> authorities;

    public AdminDetails(Admin admin, List<RoleGrantedAuthority> authorities) {
        this.id = admin.getId();
        this.roleId = admin.getRole().getId();
        this.roleName = admin.getRole().getName();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.nickname = admin.getNickname();
        this.avatar = admin.getAvatar();
        this.email = admin.getEmail();
        this.mobile = admin.getMobile();
        this.state = admin.getState();
        this.role = admin.getRole();
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.state == State.enable.value();
    }
}
