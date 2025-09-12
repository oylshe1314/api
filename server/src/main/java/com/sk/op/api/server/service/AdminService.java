package com.sk.op.api.server.service;

import com.sk.op.api.common.exception.StandardResponseException;
import com.sk.op.api.server.auth.AdminDetails;
import com.sk.op.api.server.dto.*;
import com.sk.op.api.server.entity.Admin;
import com.sk.op.api.server.entity.Role;
import com.sk.op.api.server.entity.base.State;
import com.sk.op.api.server.repository.AdminRepository;
import com.sk.op.api.server.repository.RoleRepository;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
public class AdminService {

    private static final ObjectId DEFAULT_ADMIN_ID = new ObjectId("000000000000000000000001");

    private PasswordEncoder passwordEncoder;

    private AdminRepository adminRepository;

    private RoleRepository roleRepository;

    public Admin get(ObjectId id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin get(String id) {
        return this.get(new ObjectId(id));
    }

    public Admin getByUsername(String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }

    public Page<Admin> query(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    public Page<Admin> query(Pageable pageable, AdminQueryDto dto) {
        if (dto == null) {
            return query(pageable);
        }
        Admin admin = new Admin();
        if (dto.getRoleId() != null) {
            admin.setRoleId(new ObjectId(dto.getRoleId()));
        }
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (dto.getUsername() != null) {
            admin.setUsername(dto.getUsername());
            matcher = matcher.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        }

        return adminRepository.findAll(Example.of(admin, matcher), pageable);
    }

    public Admin add(AdminAddDto dto, AdminDetails operator) {
        if (adminRepository.existsByIdNotAndUsername(null, dto.getUsername())) {
            throw new StandardResponseException("用户名已存在");
        }
        if (adminRepository.existsByIdNotAndNickname(null, dto.getNickname())) {
            throw new StandardResponseException("昵称已存在");
        }

        Role role = roleRepository.findById(new ObjectId(dto.getRoleId())).orElseThrow(() -> new StandardResponseException("角色不存在"));
        if (role.getState() == State.disable.value()) {
            throw new StandardResponseException("角色已禁用");
        }

        Admin admin = new Admin();
        admin.setRoleId(role.getId());
        admin.setUsername(dto.getUsername());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setNickname(dto.getNickname());
        admin.setAvatar(ObjectUtils.defaultIfNull(dto.getAvatar(), ""));
        admin.setEmail(ObjectUtils.defaultIfNull(dto.getEmail(), ""));
        admin.setMobile(ObjectUtils.defaultIfNull(dto.getMobile(), ""));
        admin.setState(State.enable.value());
        admin.initOperation(dto.getRemark(), operator.getUsername());

        admin.setRole(role);
        admin = adminRepository.save(admin);

        return admin;
    }

    public Admin update(AdminUpdateDto dto, AdminDetails operator) {
        ObjectId id = new ObjectId(dto.getId());
        if (id.equals(DEFAULT_ADMIN_ID)) {
            throw new StandardResponseException("默认管理员禁止修改");
        }

        Admin admin = this.get(id);
        if (admin == null) {
            throw new StandardResponseException("ID找不到记录");
        }

        if (dto.getRoleId() != null) {
            Role role = roleRepository.findById(new ObjectId(dto.getRoleId())).orElseThrow(() -> new StandardResponseException("角色不存在"));
            if (role.getState() == State.disable.value()) {
                throw new StandardResponseException("角色已禁用");
            }
            admin.setRoleId(role.getId());
            admin.setRole(role);
        }
        if (dto.getPassword() != null) {
            admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getNickname() != null) {
            if (adminRepository.existsByIdNotAndNickname(admin.getId(), dto.getNickname())) {
                throw new StandardResponseException("昵称已存在");
            }
            admin.setNickname(dto.getNickname());
        }
        if (dto.getAvatar() != null) {
            admin.setAvatar(dto.getAvatar());
        }
        if (dto.getEmail() != null) {
            admin.setEmail(dto.getEmail());
        }
        if (dto.getMobile() != null) {
            admin.setMobile(dto.getMobile());
        }

        admin.updateOperation(dto.getRemark(), operator.getUsername());

        return adminRepository.save(admin);
    }

    public void delete(DeleteDto dto) throws Exception {
        Set<ObjectId> ids = dto.getIds().stream().map(ObjectId::new).collect(Collectors.toSet());
        if (ids.contains(DEFAULT_ADMIN_ID)) {
            throw new StandardResponseException("默认管理员禁止删除");
        }
        adminRepository.deleteAllById(ids);
    }

    public void changeState(ChangeStateDto dto, AdminDetails operator) throws Exception {
        Set<ObjectId> ids = dto.getIds().stream().map(ObjectId::new).collect(Collectors.toSet());
        if (dto.getState() == State.disable.value() && ids.contains(DEFAULT_ADMIN_ID)) {
            throw new StandardResponseException("默认管理员禁止禁用");
        }
        adminRepository.updateState(ids, dto.getState(), operator.getUsername(), LocalDateTime.now());
    }

    public boolean existsByRoleIds(Set<ObjectId> roleIds) {
        return adminRepository.existsByRoleIdIn(roleIds);
    }
}
