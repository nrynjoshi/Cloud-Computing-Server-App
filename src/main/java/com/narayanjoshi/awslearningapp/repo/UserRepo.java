package com.narayanjoshi.awslearningapp.repo;

import com.narayanjoshi.awslearningapp.model.ApiClient;
import com.narayanjoshi.awslearningapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, String> {

    User findByEmail(String email);

}
