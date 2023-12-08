package com.narayanjoshi.awslearningapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "app_client", uniqueConstraints = {}) //unique name and token
@Data
public class ApiClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    private String token;

    private boolean isAdmin = false;

}
