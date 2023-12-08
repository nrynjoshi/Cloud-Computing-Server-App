package com.narayanjoshi.awslearningapp.endpoint;

import com.narayanjoshi.awslearningapp.dto.RequestDTO;
import com.narayanjoshi.awslearningapp.dto.ResponseDTO;
import com.narayanjoshi.awslearningapp.model.User;
import com.narayanjoshi.awslearningapp.service.UserService;
import com.narayanjoshi.awslearningapp.util.TokenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserAccessEndpoint {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenConfig tokenConfig;

    @PostMapping(value = "/login")
    public ResponseDTO login(@RequestBody RequestDTO request) {
        Map<String, Object> requestData = request.getData();
        String username = (String) requestData.get("username");
        String password = (String) requestData.get("password");
        System.out.println("username:" + username + ", password=" + password);

        String token = userService.authenticate(username, password);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setItem("token", token);
        responseDTO.setItem("username", username);
        return responseDTO;
    }

    @PostMapping(value = "/registration")
    public ResponseDTO registration(@RequestBody RequestDTO request) {
        Map<String, Object> requestData = request.getData();
        userService.registration(requestData);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Registration successfully");
        return responseDTO;
    }

    @GetMapping(path = "/profile")
    public ResponseDTO profileInfo(@RequestHeader("token") String token) {
        tokenConfig.validateUserAccessToken(token);
        ResponseDTO responseDTO = new ResponseDTO();
        User profile = userService.getProfile();
        responseDTO.setItem(userService.getObjectMapper().convertValue(profile, Map.class));
        return responseDTO;
    }

    @PostMapping(value = "/upload/profile")
    public ResponseDTO uploadProfile(@RequestHeader("token") String token, @RequestParam("file") MultipartFile file) throws IOException {
        tokenConfig.validateUserAccessToken(token);
//        userService.uploadProfile(file);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Upload successfully");
        return responseDTO;
    }

    @GetMapping(path = "/view/profile-image/{id}")
    public ResponseDTO downloadFile(@PathVariable String id) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setItem("link", userService.getProfileImageKey());
        return responseDTO;
    }
}
