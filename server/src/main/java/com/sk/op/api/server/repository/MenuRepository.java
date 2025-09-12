package com.sk.op.api.server.repository;

import com.sk.op.api.server.entity.Menu;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface MenuRepository extends MongoRepository<Menu, ObjectId> {

    List<Menu> findAllByType(Integer type);

    List<Menu> findAllByState(Integer state);

    List<Menu> findAllByIdInAndState(Collection<ObjectId> ids, Integer state);

    boolean existsByIdNotAndTypeAndPath(ObjectId id, Integer type, String name);

    boolean existsByIdNotAndParentIdAndName(ObjectId id, ObjectId parentId, String name);

    boolean existsAllByParentIdIn(Collection<ObjectId> parentId);

    @Query("{_id:{$in:?0}}")
    @Update("{$set:{'state':?1, 'updateBy':?2, 'updateTime': ?3}}")
    void updateState(Collection<ObjectId> ids, Integer state, String updateBy, LocalDateTime updateTime);
}
