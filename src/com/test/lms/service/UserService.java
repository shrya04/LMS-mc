package com.test.lms.service;

import com.test.lms.models.User;
import com.test.lms.repository.UserRepository;

public class UserService {
    public static void adduser(User user) {
        UserRepository.save(user);
        System.out.println("User Saved successfully " +user.getName());
    }

    public static void removeUser(String userid) {
        UserRepository.deleteUser(userid);
    }
}
