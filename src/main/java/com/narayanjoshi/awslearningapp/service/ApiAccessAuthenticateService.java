package com.narayanjoshi.awslearningapp.service;

import com.narayanjoshi.awslearningapp.config.ApiKeyAuthentication;
import com.narayanjoshi.awslearningapp.exception.InValidCredentials;
import com.narayanjoshi.awslearningapp.model.ApiClient;
import com.narayanjoshi.awslearningapp.repo.ApiClientRepo;
import com.narayanjoshi.awslearningapp.util.TokenConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class ApiAccessAuthenticateService {

    @Autowired
    private ApiClientRepo apiClientRepo;

    public Authentication getAuth(HttpServletRequest request) {
        String apiKey = request.getHeader(TokenConfig.AUTH_TOKEN_HEADER_PREFIX);


        ApiClient byToken = apiClientRepo.findByToken(apiKey);
        if (byToken == null) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

}
