package com.narayanjoshi.awslearningapp.util;

import com.narayanjoshi.awslearningapp.exception.InValidCredentials;
import com.narayanjoshi.awslearningapp.model.User;
import com.narayanjoshi.awslearningapp.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.Optional;

@Component
public class TokenConfig {

    @Autowired private PasswordEncoder encoder;
    @Autowired private UserRepo userRepo;

    public static final String AUTH_TOKEN_HEADER_PREFIX = "API-AUTH";



    public void  validateUserAccessToken(String tokenVal){
        String[] tokenSplit = StringUtils.split(tokenVal, "-", 2);
        String id = tokenSplit[0];
        String token = tokenSplit[1];
        Optional<User> byId = userRepo.findById(id);
        if(byId.isPresent()){
            User user = byId.get();
            String tokenRaw = prepareHashTokenCombination(user);
            if(encoder.matches(tokenRaw, token)){
                return;
            }
        }
        throw new InValidCredentials("Access token does not match.");
    }

    public String createUserAccessToken(User byEmail){
        String tokenRaw = prepareHashTokenCombination(byEmail);
        return byEmail.getId()+"-"+encoder.encode(tokenRaw);
    }

    public String prepareHashTokenCombination(User byEmail){
        return byEmail.getEmail()+";"+byEmail.getId()+";"+getAPIAuthToken();
    }

    public String getAPIAuthToken(){
        HttpServletRequest request =
                ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                        .getRequest();
        return request.getHeader(AUTH_TOKEN_HEADER_PREFIX);
    }

}
