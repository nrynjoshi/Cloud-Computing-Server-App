package com.narayanjoshi.awslearningapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    public Map<String, Object> item;

    private String message;

    public void setItem(String key, String name){
        if(item == null){
            item = new HashMap<>();
        }

        item.put(key, name);
    }

    public Map<String, Object> getItem() {
        return item;
    }

    public void setItem(Map<String, Object> item) {
        this.item = item;
    }
}
