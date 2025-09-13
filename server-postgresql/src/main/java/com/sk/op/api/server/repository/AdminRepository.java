package com.sk.op.api.server.repository;

import com.sk.op.api.server.entity.Admin;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @EntityGraph(value = "adminWithRole", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Admin> findByUsername(String username);

    boolean existsByRoleIdIn(Collection<Long> roleIds);

    boolean existsByIdNotAndUsername(Long id, String username);

    boolean existsByIdNotAndNickname(Long id, String username);

    @Modifying
    @Transactional
    @Query("UPDATE Admin SET state=:state, updateBy=:updateBy, updateTime=:updateTime WHERE id IN (:ids)")
    void updateState(@Param("ids") Collection<Long> ids, @Param("state") Integer state, @Param("updateBy") String updateBy, @Param("updateTime") LocalDateTime updateTime);

    @Modifying
    @Transactional
    @Query("UPDATE Admin SET nickname=:nickname, avatar=:avatar, email=:email, mobile=:mobile WHERE id=:id")
    void updateDetail(@Param("id") Long id, @Param("nickname") String nickname, @Param("avatar") String avatar, @Param("email") String email, @Param("mobile") String mobile);

    @Modifying
    @Transactional
    @Query("UPDATE Admin SET password=:password WHERE id=:id")
    void updatePassword(@Param("id") Long id, @Param("password") String password);
}
