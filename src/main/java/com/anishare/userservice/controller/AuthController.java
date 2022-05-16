package com.anishare.userservice.controller;

import com.anishare.userservice.VO.RegisterUser;
import com.anishare.userservice.entity.User;
import com.anishare.userservice.exceptions.DuplicateUserNameException;
import com.anishare.userservice.service.UserDetailService;
import com.anishare.userservice.service.UserService;
import com.anishare.userservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger("AuthController.class");

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            if (authentication.isAuthenticated()) {
                logger.info("Logged In");
                User user = userService.findUserByUsername(userName);
                String token = jwtUtil.generateToken(user);
                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", token);
                return ResponseEntity.status(200).body(responseMap);
            } else {
                responseMap.put("error", true);
                responseMap.put("message", "Invalid Credentials");
                return ResponseEntity.status(401).body(responseMap);
            }
        } catch (DisabledException e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "User is Disabled");
            logger.error("User is Disabled");
            return ResponseEntity.status(500).body(responseMap);
        } catch (Exception e) {
            responseMap.put("error", true);
            responseMap.put("message", "User is Disabled");
            logger.error("User is Disabled");
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUser registerUser) throws DuplicateUserNameException{
        Map<String, Object> responseMap = new HashMap<>();
        try {
            if (registerUser != null) {
                User user = new User();
                if (!registerUser.getUserName().isBlank() && !registerUser.getPassword().isBlank()) {
                    user.setUserName(registerUser.getUserName());
                    user.setPassword(new BCryptPasswordEncoder().encode(registerUser.getPassword()));
                    user.setEnabled(true);
                    user.setAccountNonExpired(true);
                    user.setAccountNonLocked(true);
                    user.setCredentialsNonExpired(true);
                    User resultUser = userService.saveUser(user);
                    if (resultUser == null) {
                        throw new DuplicateUserNameException(registerUser.getUserName());
                    }
                    String token = jwtUtil.generateToken(user);
                    responseMap.put("error", false);
                    responseMap.put("message", "User Registered Successfully");
                    responseMap.put("token", token);
                    return ResponseEntity.status(HttpStatus.OK).body(responseMap);


                } else {
                    throw new BadCredentialsException("Empty Username or Password");
                }
            } else {
                throw new IllegalArgumentException("Null registerUser received");
            }
        } catch (IllegalArgumentException e) {
            responseMap.put("error", true);
            responseMap.put("message", "Bad Request");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        
    }

}
