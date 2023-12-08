package com.narayanjoshi.awslearningapp.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RequestDTO {

    private Map<String, Object> data;
}
