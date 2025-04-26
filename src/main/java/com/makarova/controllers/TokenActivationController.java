package com.makarova.controllers;

import com.makarova.models.User;
import com.makarova.repository.UserRepository;
import com.makarova.services.TokenService;
import com.makarova.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TokenActivationController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/activate")
    public String activateAccount(@RequestParam String token) {
        User user = userService.findByActivationToken(token);


        user.setActivated(true);
        user.setActivationToken(null);

        userService.activateUser(token);

        return "activation-success";
    }
}