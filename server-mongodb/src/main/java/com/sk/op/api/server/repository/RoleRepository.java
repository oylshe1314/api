package com.sk.op.api.server.repository;

import com.sk.op.api.server.entity.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    List<Role> findAllByState(Integer state);

    boolean existsByIdNotAndName(ObjectId id, String name);

    @Query("{_id:{$in:?0}}")
    @Update("{$set:{'state':?1, 'updateBy':?2, 'updateTime': ?3}}")
    void updateState(Collection<ObjectId> ids, Integer state, String updateBy, LocalDateTime updateTime);

    @Query("{_id:?0}")
    @Update("{$set:{'authorities':?1}}")
    void updateMenuIds(ObjectId id, Collection<ObjectId> menuIds);
}
