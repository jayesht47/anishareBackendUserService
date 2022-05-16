package com.anishare.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControlAdvisor {


    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException e) {
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("error", false);
        responseMap.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
    }

    @ExceptionHandler(value = DuplicateUserNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> duplicateUserNameFoundExceptionHandler(DuplicateUserNameException e)
    {
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("error", false);
        responseMap.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> badCredentialsExceptionHandler(BadCredentialsException e)
    {
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("error", true);
        responseMap.put("message", "Invalid Credentials");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
    }



}
