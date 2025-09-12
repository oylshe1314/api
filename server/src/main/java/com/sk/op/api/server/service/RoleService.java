package com.sk.op.api.server.service;

import com.sk.op.api.common.exception.StandardResponseException;
import com.sk.op.api.server.auth.AdminDetails;
import com.sk.op.api.server.dto.*;
import com.sk.op.api.server.entity.Role;
import com.sk.op.api.server.entity.base.State;
import com.sk.op.api.server.repository.AdminRepository;
import com.sk.op.api.server.repository.RoleRepository;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
public class RoleService {

    protected static final ObjectId DEFAULT_ROLE_ID = new ObjectId("000000000000000000000001");

    private RoleRepository roleRepository;
    private AdminRepository adminRepository;

    public Role get(ObjectId id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role get(String id) {
        return this.get(new ObjectId(id));
    }

    public List<Role> getAll(State state) {
        return roleRepository.findAllByState(state.value());
    }

    public Page<Role> query(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Page<Role> query(Pageable pageable, RoleQueryDto dto) {
        if (dto == null) {
            return query(pageable);
        }
        Role params = new Role();
        if (dto.getName() != null) {
            params.setName(dto.getName());
        }
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        return roleRepository.findAll(Example.of(params, matcher), pageable);
    }

    public Role add(RoleAddDto dto, AdminDetails operator) {
        if (roleRepository.existsByIdNotAndName(null, dto.getName())) {
            throw new StandardResponseException("角色已存在");
        }

        Role role = new Role();
        role.setName(dto.getName());
        role.setState(State.enable.value());
        role.initOperation(dto.getRemark(), operator.getUsername());

        return roleRepository.save(role);
    }

    public Role update(RoleUpdateDto dto, AdminDetails operator) {
        ObjectId id = new ObjectId(dto.getId());
        if (id.equals(DEFAULT_ROLE_ID)) {
            throw new StandardResponseException("默认角色禁止修改");
        }

        Role role = this.get(id);
        if (role == null) {
            throw new StandardResponseException("ID找不到记录");
        }

        if (dto.getName() != null) {
            if (roleRepository.existsByIdNotAndName(role.getId(), dto.getName())) {
                throw new StandardResponseException("角色已存在");
            }
            role.setName(dto.getName());
        }

        role.updateOperation(dto.getRemark(), operator.getUsername());

        return roleRepository.save(role);
    }

    public void delete(DeleteDto dto) throws Exception {
        Set<ObjectId> ids = dto.getIds().stream().map(ObjectId::new).collect(Collectors.toSet());
        if (ids.contains(DEFAULT_ROLE_ID)) {
            throw new StandardResponseException("默认角色禁止删除");
        }
        if (adminRepository.existsByRoleIdIn(ids)) {
            throw new StandardResponseException("请先删除角色下的管理员");
        }

        roleRepository.deleteAllById(ids);
    }

    public void changeState(ChangeStateDto dto, AdminDetails operator) throws Exception {
        Set<ObjectId> ids = dto.getIds().stream().map(ObjectId::new).collect(Collectors.toSet());
        if (dto.getState() == State.disable.value() && ids.contains(DEFAULT_ROLE_ID)) {
            throw new StandardResponseException("默认角色禁止禁用");
        }

        roleRepository.updateState(ids, dto.getState(), operator.getUsername(), LocalDateTime.now());
    }

    public void setAuthorities(RoleSetMenusDto dto) {
        Role role = this.get(new ObjectId(dto.getRoleId()));
        if (role == null) {
            throw new StandardResponseException("ID找不到记录");
        }

        Set<ObjectId> menuIds = dto.getMenuIds().stream().map(ObjectId::new).collect(Collectors.toSet());
        if (DEFAULT_ROLE_ID.equals(role.getId())) {
            MenuService.SYSTEM_MENU_IDS.forEach(sysMenuId -> {
                if (!menuIds.contains(sysMenuId)) {
                    throw new StandardResponseException("默认角色禁止禁用");
                }
            });
        }

        if (menuIds.isEmpty()) {
            return;
        }

        roleRepository.updateAuthorities(role.getId(), menuIds);
    }
}
