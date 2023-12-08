package com.narayanjoshi.awslearningapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class User {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String addressLine1;

    private String city;

    private String postCode;

    private String phoneNumber;

    private Date dateOfBirth;

    @Column(unique = true)
    private String email;


    private String password;

    @JsonIgnore
    private String emailConfirmationCode;

    @JsonIgnore
    private boolean isEmailConfirmed;

}
