package com.makarova.services;

import com.makarova.dto.UserRegisterDto;
import com.makarova.models.User;
import com.makarova.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CurrencyService currencyService;

    public void saveUser(UserRegisterDto userRegisterDto) {
        if(userRepository.findByUsername(userRegisterDto.getUsername()).isPresent()) {
            throw new RuntimeException("ошибка");
        } else {
            User user = new User();
            user.setUsername(userRegisterDto.getUsername());
            user.setPassword(userRegisterDto.getPassword());
            user.setEmail(userRegisterDto.getEmail());

            String token = tokenService.generateToken();
            user.setActivationToken(token);

            userRepository.save(user);
            emailService.sendEMail(userRegisterDto.getEmail(), token);

            String exchangeRate = currencyService.getUsdToRubExchangeRate();
            emailService.sendEMail(userRegisterDto.getEmail(), exchangeRate);
        }
    }

    public User findByActivationToken(String token) {
        return userRepository.findByActivationToken(token).orElseThrow(RuntimeException::new);
    }

    public void activateUser(String token) {
        User user = userRepository.findByActivationToken(token)
                .orElseThrow(() -> new UsernameNotFoundException("activation token not found"));
        user.setActivated(true);
        userRepository.saveAndFlush(user);
    }

}
