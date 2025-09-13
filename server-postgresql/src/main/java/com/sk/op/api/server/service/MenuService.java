package com.sk.op.api.server.service;

import com.sk.op.api.common.exception.StandardResponseException;
import com.sk.op.api.server.auth.AdminDetails;
import com.sk.op.api.server.dto.*;
import com.sk.op.api.server.entity.Menu;
import com.sk.op.api.server.entity.base.MenuType;
import com.sk.op.api.server.entity.base.State;
import com.sk.op.api.server.repository.MenuRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Setter(onMethod_ = @Autowired)
public class MenuService {

    protected static final Set<Long> SYSTEM_MENU_IDS;

    static {
        SYSTEM_MENU_IDS = new HashSet<>();
        SYSTEM_MENU_IDS.add(1L);
        SYSTEM_MENU_IDS.add(2L);
        SYSTEM_MENU_IDS.add(3L);
        SYSTEM_MENU_IDS.add(4L);
        SYSTEM_MENU_IDS.add(5L);
        SYSTEM_MENU_IDS.add(6L);
        SYSTEM_MENU_IDS.add(7L);
        SYSTEM_MENU_IDS.add(8L);
        SYSTEM_MENU_IDS.add(9L);
        SYSTEM_MENU_IDS.add(10L);
        SYSTEM_MENU_IDS.add(11L);
        SYSTEM_MENU_IDS.add(12L);
        SYSTEM_MENU_IDS.add(13L);
        SYSTEM_MENU_IDS.add(14L);
        SYSTEM_MENU_IDS.add(15L);
        SYSTEM_MENU_IDS.add(16L);
        SYSTEM_MENU_IDS.add(17L);
        SYSTEM_MENU_IDS.add(18L);
        SYSTEM_MENU_IDS.add(19L);
        SYSTEM_MENU_IDS.add(20L);
        SYSTEM_MENU_IDS.add(21L);
        SYSTEM_MENU_IDS.add(22L);
        SYSTEM_MENU_IDS.add(23L);
        SYSTEM_MENU_IDS.add(24L);
        SYSTEM_MENU_IDS.add(25L);
        SYSTEM_MENU_IDS.add(26L);
        SYSTEM_MENU_IDS.add(27L);
    }

    private MenuRepository menuRepository;

    public Menu get(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    public List<Menu> getAll(State state) {
        return menuRepository.findAllByState(state.value());
    }

    public List<Menu> getAll(Integer type) {
        return menuRepository.findAllByType(type);
    }

    public Page<Menu> query(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    public Page<Menu> query(Pageable pageable, MenuQueryDto dto) {
        if (dto == null) {
            return query(pageable);
        }
        Menu params = new Menu();
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (dto.getType() != null) {
            params.setType(dto.getType());
        }
        if (dto.getParentName() != null) {
            params.setParent(new Menu());
            params.getParent().setName(dto.getParentName());
            matcher = matcher.withMatcher("parent.name", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (dto.getName() != null) {
            params.setName(dto.getName());
            matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (dto.getPath() != null) {
            params.setPath(dto.getPath());
            matcher = matcher.withMatcher("path", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        return menuRepository.findAll(Example.of(params, matcher), pageable);
    }

    @Transactional
    public Menu add(MenuAddDto dto, AdminDetails operator) {
        Menu parent = null;
        if (dto.getType() != MenuType.dir.value()) {
            if (dto.getParentId() == null || dto.getParentId() <= 0) {
                throw new StandardResponseException("上级菜单ID错误");
            }
            parent = this.get(dto.getParentId());
            if (parent == null) {
                throw new StandardResponseException("上级菜单不存在");
            }
        }

        MenuType menuType = MenuType.valueOf(dto.getType());
        if (menuType == null) {
            throw new StandardResponseException("菜单类型错误");
        }

        if (menuRepository.existsByIdNotAndParentIdAndName(null, dto.getParentId(), dto.getName())) {
            throw new StandardResponseException("已存在相同名称的菜单");
        }

        if (menuType != MenuType.dir) {
            if (menuRepository.existsByIdNotAndTypeAndPath(null, menuType.value(), dto.getPath())) {
                throw new StandardResponseException("已存在相同路径的菜单");
            }
        }

        Menu menu = new Menu();
        menu.setType(menuType.value());
        menu.setParentId(dto.getParentId());
        menu.setIcon(menuType == MenuType.api ? "" : dto.getIcon());
        menu.setName(dto.getName());
        menu.setPath(menuType == MenuType.dir ? "" : dto.getPath());
        menu.setSortBy(dto.getSortBy());
        menu.setState(State.disable.value());
        menu.initOperation(dto.getRemark(), operator.getUsername());

        menu.setParent(parent);
        menu = menuRepository.save(menu);

        return menu;
    }

    @Transactional
    public Menu update(MenuUpdateDto dto, AdminDetails operator) {
        Menu menu = this.get(dto.getId());
        if (menu == null) {
            throw new StandardResponseException("ID找不到记录");
        }
        if (dto.getType() != null) {
            MenuType menuType = MenuType.valueOf(dto.getType());
            if (menuType == null) {
                throw new StandardResponseException("菜单类型错误");
            }
            menu.setType(menuType.value());
            if (menuType != MenuType.dir) {
                if (menuRepository.existsByIdNotAndTypeAndPath(menu.getId(), menuType.value(), dto.getPath() == null ? menu.getPath() : dto.getPath())) {
                    throw new StandardResponseException("已存在相同路径的菜单");
                }
            }
        }
        if (dto.getParentId() != null) {
            if (menu.getType() == MenuType.dir.value()) {
                menu.setParentId(null);
                menu.setParent(null);
            } else {
                if (dto.getParentId() <= 0) {
                    throw new StandardResponseException("上级菜单ID错误");
                }
                if (menuRepository.existsByIdNotAndParentIdAndName(menu.getId(), menu.getParentId(), dto.getName() == null ? menu.getName() : dto.getName())) {
                    throw new StandardResponseException("已存在相同名称的菜单");
                }
                Menu parent = this.get(dto.getParentId());
                if (parent == null) {
                    throw new StandardResponseException("上级菜单不存在");
                }
                menu.setParentId(dto.getParentId());
                menu.setParent(parent);
            }
        }
        if (dto.getIcon() != null) {
            menu.setIcon(menu.getType() == MenuType.api.value() ? "" : dto.getIcon());
            if (menuRepository.existsByIdNotAndParentIdAndName(menu.getId(), menu.getParentId(), menu.getName())) {
                throw new StandardResponseException("已存在相同名称的菜单");
            }
        }
        if (dto.getName() != null) {
            menu.setName(dto.getName());
            if (menuRepository.existsByIdNotAndParentIdAndName(menu.getId(), menu.getParentId(), menu.getName())) {
                throw new StandardResponseException("已存在相同名称的菜单");
            }
        }
        if (dto.getPath() != null) {
            if (menu.getType() == MenuType.dir.value()) {
                menu.setPath("");
            } else {
                menu.setPath(dto.getPath());
                if (menuRepository.existsByIdNotAndTypeAndPath(menu.getId(), menu.getType(), menu.getPath())) {
                    throw new StandardResponseException("已存在相同路径的菜单");
                }
            }
        }
        if (dto.getSortBy() != null) {
            menu.setSortBy(dto.getSortBy());
        }

        menu.updateOperation(dto.getRemark(), operator.getUsername());

        return menuRepository.save(menu);
    }

    @Transactional
    public void delete(DeleteDto dto) {
        Set<Long> ids = new HashSet<>(dto.getIds());

        if (menuRepository.existsAllByParentIdIn(ids)) {
            throw new StandardResponseException("请先删除子菜单");
        }

        menuRepository.deleteAllById(ids);
    }

    @Transactional
    public void changeState(ChangeStateDto dto, AdminDetails operator) {
        menuRepository.updateState(new HashSet<>(dto.getIds()), dto.getState(), operator.getUsername(), LocalDateTime.now());
    }
}
