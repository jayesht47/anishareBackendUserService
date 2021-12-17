package com.anishare.userservice.exceptions;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(long userId)
    {
        super("user not found for id "+userId);
    }

}
