package com.sda.chat.infrastructure.memory;

import com.sda.chat.domain.model.ChatUser;
import com.sda.chat.domain.port.UsersRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUsersInfratructure implements UsersRepository {

    private List<ChatUser> users;

    public InMemoryUsersInfratructure(List <ChatUser> users) {
        this.users = new ArrayList <>();
    }

    public void addUser(ChatUser user) {
        users.add(user);
    }
}
