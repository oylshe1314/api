package com.sk.op.api.server.repository;

import com.sk.op.api.server.entity.RoleMenuRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleMenuRelationRepository extends JpaRepository<RoleMenuRelation, Long>, JpaSpecificationExecutor<RoleMenuRelation> {

    @Transactional
    void deleteAllByRoleId(Long roleId);
}
