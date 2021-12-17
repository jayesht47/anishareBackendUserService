package com.anishare.userservice.repository;

import com.anishare.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUserId(Long userId);

    User findByUserName(String userName);
}
