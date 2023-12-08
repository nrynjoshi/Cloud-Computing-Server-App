package com.narayanjoshi.awslearningapp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.narayanjoshi.awslearningapp.exception.InValidCredentials;
import com.narayanjoshi.awslearningapp.model.User;
import com.narayanjoshi.awslearningapp.repo.UserRepo;
import com.narayanjoshi.awslearningapp.util.ServletRequestUtil;
import com.narayanjoshi.awslearningapp.util.TokenConfig;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenConfig tokenConfig;

    @Autowired
    private AwsS3Service awsS3Service;

    @Autowired
    private ServletRequestUtil servletRequestUtil;

    public String authenticate(String username, String password) {
        User byEmail = getByEmail(username);
        if (byEmail == null) {
            throw new InValidCredentials("Username does not exist.");
        }
        String validPassword = byEmail.getPassword();
        if (!encoder.matches(password, validPassword)) {
            throw new InValidCredentials("Username/Password does not exist.");
        }

        return tokenConfig.createUserAccessToken(byEmail);
    }

    private User getByEmail(String username) {
        User byEmail = userRepo.findByEmail(username);
        return byEmail;
    }

    public User getProfile() {
        long userId = servletRequestUtil.getAuthUserId();
        Optional<User> byEmail = userRepo.findById(String.valueOf(userId));
        if(byEmail.isPresent()){
            User user = byEmail.get();
            user.setPassword(null);
            return user;
        }
        throw new InValidCredentials("Something went wrong.");
    }
    public void registration(Map<String, Object> map) {

        ObjectMapper mapper = getObjectMapper();
        User user = mapper.convertValue(map, User.class);

        user.setPassword(encoder.encode(user.getPassword()));

       User byEmail= getByEmail(user.getEmail());
        if (byEmail != null) {
            throw new InValidCredentials("Username already exist.");
        }

        userRepo.save(user);


    }

    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public void uploadProfile(MultipartFile profile) throws IOException {
        awsS3Service.uploadFile(profile.getBytes(), servletRequestUtil.getAuthUserId());
    }

    public String getProfileImageLink() {

        return awsS3Service.generatePresignedUrl(servletRequestUtil.getAuthUserId());
    }

}
