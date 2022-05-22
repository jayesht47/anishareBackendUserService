package com.anishare.userservice.VO;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class LoginUser {

    private String userName;

    private String password;

}
