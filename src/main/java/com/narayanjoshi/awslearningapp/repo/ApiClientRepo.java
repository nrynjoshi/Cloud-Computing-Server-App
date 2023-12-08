package com.narayanjoshi.awslearningapp.repo;

import com.narayanjoshi.awslearningapp.model.ApiClient;
import com.narayanjoshi.awslearningapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiClientRepo extends CrudRepository<ApiClient, String> {

    ApiClient findByToken(String token);

}
