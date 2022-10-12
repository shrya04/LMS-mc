package com.test.lms.repository;

import com.test.lms.models.User;

import java.util.HashMap;

public class UserRepository {

    static HashMap<String, User> userHashMap;

    public static void save(User user) {
        if(userHashMap == null) {
            userHashMap = new HashMap<>();
        }
        if(userHashMap.containsKey(user.getId())){
            throw new RuntimeException("user already registered");
        }
        userHashMap.put(user.getId(), user);
    }

    public static User getUserFromId(String userId) {
        if(!userHashMap.containsKey(userId)){
            throw new RuntimeException("user Id not found");
        }
        return userHashMap.get(userId);
    }

    public static void deleteUser(String userid) {
        userHashMap.remove(userid);
        System.out.println("User removed successfully");
    }
}
