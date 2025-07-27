package com.mosesidowu.user_service.data.repository;

import com.mosesidowu.user_service.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    User findUserByEmail(String email);
}
