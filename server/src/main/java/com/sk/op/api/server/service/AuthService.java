package com.sk.op.api.server.service;

import com.sk.op.api.common.exception.StandardResponseException;
import com.sk.op.api.server.auth.AdminDetails;
import com.sk.op.api.server.auth.RoleGrantedAuthority;
import com.sk.op.api.server.entity.Admin;
import com.sk.op.api.server.entity.Menu;
import com.sk.op.api.server.entity.base.State;
import com.sk.op.api.server.repository.AdminRepository;
import com.sk.op.api.server.repository.MenuRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter(onMethod_ = @Autowired)
public class AuthService {

    private PasswordEncoder passwordEncoder;

    private AdminRepository adminRepository;
    private MenuRepository menuRepository;

    public AdminDetails login(String username, String password) throws AuthenticationException {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("", new StandardResponseException("用户名或者密码错误")));
        if (admin.getRole().getState() != State.enable.value()) {
            throw new StandardResponseException("角色已禁用");
        }

        if (admin.getState() != State.enable.value()) {
            throw new StandardResponseException("管理员已禁用");
        }

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new BadCredentialsException("", new StandardResponseException("用户名或者密码错误"));
        }

        List<Menu> menus = menuRepository.findAllByIdInAndState(admin.getRole().getMenuIds(), State.enable.value());
        return new AdminDetails(admin, menus.stream().map(menu -> new RoleGrantedAuthority(menu.getPath())).toList());
    }

    public void logout() {

    }
}
