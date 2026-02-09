package com.example.e_wallet.controllers;
import com.example.e_wallet.Dto.LoginRequest;
import com.example.e_wallet.Dto.LoginResponse;
import com.example.e_wallet.JwtUtil;
import com.example.e_wallet.Models.UserModels;
import com.example.e_wallet.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){

        UserModels user = userService.login(
              request.getUsername(),
              request.getPassword()
        );

        String token = jwtUtil.generateToken(user.getUserName());

        return new LoginResponse(
                "Login Successful",
                user.getUserId(),
                token
        );
    }
}
