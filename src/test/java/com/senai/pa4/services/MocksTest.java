package com.senai.pa4.services;

import com.senai.pa4.entities.User;

import java.util.List;

public class MocksTest {

    public List<User> mockUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("MacGyver");
        user1.setPassword("123456");
        user1.setPosition("Gerente");
        user1.setRoleType("admin");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("Jack");
        user2.setPassword("654321");
        user2.setPosition("Assistente de produção");
        user2.setRoleType("user");

        return List.of(user1, user2);
    }

    public List<User> mockUsersEmpty() {
        return List.of();
    }
}
