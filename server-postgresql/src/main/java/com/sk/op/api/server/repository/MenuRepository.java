package com.sk.op.api.server.repository;

import com.sk.op.api.server.entity.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
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
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @EntityGraph(value = "menuWithParent", type = EntityGraph.EntityGraphType.FETCH)
    List<Menu> findAllByType(Integer type);

    @EntityGraph(value = "menuWithParent", type = EntityGraph.EntityGraphType.FETCH)
    List<Menu> findAllByState(Integer state);

    boolean existsByIdNotAndTypeAndPath(Long id, Integer type, String name);

    boolean existsByIdNotAndParentIdAndName(Long id, Long parentId, String name);

    boolean existsAllByParentIdIn(Collection<Long> parentId);

    @Modifying
    @Transactional
    @Query("UPDATE Menu SET state=:state, updateBy=:updateBy, updateTime=:updateTime WHERE id IN (:ids)")
    void updateState(@Param("ids") Collection<Long> ids, @Param("state") Integer state, @Param("updateBy") String updateBy, @Param("updateTime") LocalDateTime updateTime);
}
