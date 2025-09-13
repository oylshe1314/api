package com.sk.op.api.server.repository;

import com.sk.op.api.server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByState(Integer state);

    boolean existsByIdNotAndName(Long id, String name);

    @Modifying
    @Transactional
    @Query("UPDATE Menu SET state=:state, updateBy=:updateBy, updateTime=:updateTime WHERE id IN (:ids)")
    void updateState(@Param("ids") Collection<Long> ids, @Param("state") Integer state, @Param("updateBy") String updateBy, @Param("updateTime") LocalDateTime updateTime);
}
