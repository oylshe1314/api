package com.sk.op.api.server.service;

import com.sk.op.api.server.dto.RoleSetMenusDto;
import com.sk.op.api.server.entity.RoleMenuRelation;
import com.sk.op.api.server.entity.base.MenuType;
import com.sk.op.api.server.entity.base.State;
import com.sk.op.api.server.repository.RoleMenuRelationRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Setter(onMethod_ = @Autowired)
public class RoleMenuRelationService {

    private RoleMenuRelationRepository roleApiRelationRepository;

    public List<RoleMenuRelation> getAll(Long roleId, MenuType... types) {
        return roleApiRelationRepository.findAll((root, query, criteriaBuilder) -> {
            root.fetch("menu");
            Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("roleId"), roleId), criteriaBuilder.equal(root.get("menu").get("state"), State.enable.value()));
            if (types != null) {
                CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("menu").get("type"));
                for (MenuType type : types) {
                    in.value(type.value());
                }
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("menu").get("state"), State.enable.value()));
            }
            return query.where(predicate).getRestriction();
        });
    }

    @Transactional
    public void saveRoleMenus(RoleSetMenusDto dto) {
        roleApiRelationRepository.deleteAllByRoleId(dto.getRoleId());

        List<RoleMenuRelation> relations = dto.getMenuIds().stream().map(menuId -> {
            RoleMenuRelation roleMenuRelation = new RoleMenuRelation();
            roleMenuRelation.setRoleId(dto.getRoleId());
            roleMenuRelation.setMenuId(menuId);
            return roleMenuRelation;
        }).toList();

        roleApiRelationRepository.saveAll(relations);
    }
}
