package com.sk.op.api.server.auth;

import org.springframework.security.core.GrantedAuthority;

public class RoleGrantedAuthority implements GrantedAuthority {

    private final String path;

    public RoleGrantedAuthority(String path) {
        this.path = path;
    }

    @Override
    public String getAuthority() {
        return this.path;
    }
}
