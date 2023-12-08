package com.narayanjoshi.awslearningapp.config;

import com.narayanjoshi.awslearningapp.dto.ResponseDTO;
import com.narayanjoshi.awslearningapp.exception.InValidCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InValidCredentials.class)
    public ResponseEntity InValidCredentials(InValidCredentials inValidCredentials){
        ResponseDTO responseDTO= new ResponseDTO();
        responseDTO.setMessage(inValidCredentials.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
    }
}
