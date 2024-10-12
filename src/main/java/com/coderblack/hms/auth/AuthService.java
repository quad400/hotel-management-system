package com.coderblack.hms.auth;

import com.coderblack.hms.auth.request.*;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.exception.AccountBlockedException;
import com.coderblack.hms.exception.AccountTokenExpireException;
import com.coderblack.hms.exception.ConflictException;
import com.coderblack.hms.security.JwtService;
import com.coderblack.hms.user.Role;
import com.coderblack.hms.user.User;
import com.coderblack.hms.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public DefaultResponse registerUser(RegisterRequest request) throws AccountNotFoundException {
        var findUser = userRepository.findByEmail(request.email()).isPresent();
        if (findUser) {
            throw new ConflictException(String.format("User with this %s already exists in the database", request.email()));
        }

        User user = User.builder()
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .isAccountActivate(false)
                .role(Role.GUEST)
                .token(generateToken(6))
                .tokenExpireDate(LocalDateTime.now().plusMinutes(1))
                .password(passwordEncoder.encode(request.password()))
                .build();


        userRepository.save(user);
        return new DefaultResponse("Account Created Successfully");
    }

    public String generateToken(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public DefaultResponse loginUser(LoginRequest request) throws AccountNotFoundException {

        var findUser = userRepository
                .findByEmail(request.getEmail()).orElseThrow(() ->
                        new AccountNotFoundException("Account not found")
                );

        if(!findUser.isAccountActivate()){
            throw new AccountNotFoundException("Account not activated");
        }

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );


        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());

        if(!user.isAccountNonLocked()){
            throw new AccountBlockedException("Account Has Been Blocked, Kindly contact admin");
        }
        claims.put("email", user.getEmail());
        var token = jwtService.generateToken(claims, user);

        return new DefaultResponse("User Logged in Successfully",token);
    }

    public DefaultResponse activateAccount(ActivateUserRequest request) {
        User user = userRepository
                .findByEmailAndTokenAndTokenExpireDateGreaterThan(
                        request.getEmail(),
                        request.getToken(),
                        LocalDateTime.now()
                ).orElseThrow(() -> new AccountTokenExpireException("Token has expire or Token not valid"));

        user.setToken(null);
        user.setTokenExpireDate(null);
        if (request.getType().equals("REGISTER")) {
            user.setAccountActivate(true);
        }
        userRepository.save(user);
        return new DefaultResponse("Account Verified Successfully");
    }

    public DefaultResponse resendToken(ResendTokenRequest request) throws AccountNotFoundException {
        var user = userRepository
                .findByEmailAndIsAccountActivate(
                        request.getEmail(),
                        false
                ).orElseThrow(() ->
                        new AccountNotFoundException(
                                String.format("User with this %s not found in the database or Account is already activated", request.getEmail())));

        user.setToken(generateToken(6));
        user.setTokenExpireDate(LocalDateTime.now().plusMinutes(3));

        userRepository.save(user);
        return new DefaultResponse("Token Resent Successfully");
    }
}
