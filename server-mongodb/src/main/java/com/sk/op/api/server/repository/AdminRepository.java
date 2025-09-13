package com.sk.op.api.server.repository;

import com.sk.op.api.server.entity.Admin;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, ObjectId> {

    Optional<Admin> findByUsername(String username);

    boolean existsByRoleIdIn(Collection<ObjectId> roleIds);

    boolean existsByIdNotAndUsername(ObjectId id, String username);

    boolean existsByIdNotAndNickname(ObjectId id, String username);

    @Query("{_id:{$in:?0}}")
    @Update("{$set:{'state':?1, 'updateBy':?2, 'updateTime': ?3}}")
    void updateState(Collection<ObjectId> ids, Integer state, String updateBy, LocalDateTime updateTime);

    @Query("{_id:?0}")
    @Update("{$set:{'nickname':?1, 'avatar':?2, 'email':?3, 'mobile':?4}}")
    void updateDetail(ObjectId id, String nickname, String avatar, String email, String mobile);

    @Query("{_id:?0}")
    @Update("{$set:{'password':?1}}")
    void updatePassword(ObjectId id, String password);
}
