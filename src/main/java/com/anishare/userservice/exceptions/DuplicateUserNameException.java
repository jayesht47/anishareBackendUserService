package com.anishare.userservice.exceptions;

public class DuplicateUserNameException extends Exception{
    public DuplicateUserNameException(String userName) {
        super("Duplicate UserName found :: "+userName);
    }
}
