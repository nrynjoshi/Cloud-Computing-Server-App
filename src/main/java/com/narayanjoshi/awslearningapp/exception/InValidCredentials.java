package com.narayanjoshi.awslearningapp.exception;

public class InValidCredentials extends RuntimeException{

    public InValidCredentials(String message){
        super(message);
    }
}
