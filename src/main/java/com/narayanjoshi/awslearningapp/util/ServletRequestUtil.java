package com.narayanjoshi.awslearningapp.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ServletRequestUtil {

    @Autowired
    HttpServletRequest request;
    public long getAuthUserId(){
        String token = request.getHeader("token");
        String[] tokenSplit = token.split("-", 2);
        return Long.valueOf(tokenSplit[0]);
    }

}
