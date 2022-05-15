package com.anishare.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String duplicateUserNameFoundExceptionHandler(DuplicateUserNameException e)
    {
        return e.getMessage();
    }


}
