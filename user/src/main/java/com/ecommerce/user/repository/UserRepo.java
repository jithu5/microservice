package com.ecommerce.user.repository;

import com.ecommerce.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User,String> {
}
