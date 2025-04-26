package com.makarova.controllers;

import com.makarova.dto.UserRegisterDto;
import com.makarova.models.User;
import com.makarova.repository.UserRepository;
import com.makarova.services.EmailService;
import com.makarova.services.TokenService;
import com.makarova.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class AuthController {

    @Autowired
    public UserService userService;

    @PostMapping("/register")
    public String register(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) {
        UserRegisterDto userDto = new UserRegisterDto();
        userDto.setUsername(username);
        userDto.setEmail(email);
        userDto.setPassword(password);
        userService.saveUser(userDto);
        return "redirect:/confirmation-send";
    }
}
