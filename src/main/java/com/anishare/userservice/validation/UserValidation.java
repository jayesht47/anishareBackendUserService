package com.anishare.userservice.validation;

import com.anishare.userservice.entity.User;
import com.anishare.userservice.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class UserValidation {

    @Autowired
    UserRepo userRepo;

    public boolean checkForDuplicateUserName(String userName)
    {
        boolean  result = false;

        User user = userRepo.findByUserName(userName);
        if (user != null) {
            log.warn("Duplicate userName found ::" + userName);
            result = true;
        }

        return result;
    }


}
