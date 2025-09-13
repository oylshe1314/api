package com.sk.op.api.server.auth;

import com.sk.op.api.common.exception.StandardResponseException;
import com.sk.op.api.server.entity.Admin;
import com.sk.op.api.server.entity.Menu;
import com.sk.op.api.server.entity.RoleMenuRelation;
import com.sk.op.api.server.entity.base.State;
import com.sk.op.api.server.service.AdminService;
import com.sk.op.api.server.service.RoleMenuRelationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminLoginProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    private final AdminService adminService;
    private final RoleMenuRelationService roleMenuRelationService;

    public AdminLoginProvider(PasswordEncoder passwordEncoder, AdminService adminService, RoleMenuRelationService roleMenuRelationService) {
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
        this.roleMenuRelationService = roleMenuRelationService;
    }

    @Override
    public Authentication authenticate(Authentication authRequest) throws AuthenticationException {
        Admin admin = adminService.getByUsername((String) authRequest.getPrincipal());
        if (admin == null) {
            throw new UsernameNotFoundException("", new StandardResponseException("用户名或者密码错误"));
        }

        if (admin.getRole().getState() != State.enable.value()) {
            throw new StandardResponseException("角色已禁用");
        }

        if (admin.getState() != State.enable.value()) {
            throw new StandardResponseException("管理员已禁用");
        }

        if (!passwordEncoder.matches((String) authRequest.getCredentials(), admin.getPassword())) {
            throw new BadCredentialsException("", new StandardResponseException("用户名或者密码错误"));
        }

        List<RoleMenuRelation> relations = roleMenuRelationService.getAll(admin.getRoleId());

        AdminAuthToken authResult = new AdminAuthToken(new AdminDetails(admin, relations.stream().map(relation -> new RoleGrantedAuthority(relation.getMenu().getPath())).toList()));
        authResult.setDetails(authRequest.getDetails());
        return authResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(AdminAuthToken.class);
    }
}
