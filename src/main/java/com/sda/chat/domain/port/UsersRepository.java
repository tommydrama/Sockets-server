package com.sda.chat.domain.port;

import com.sda.chat.domain.model.ChatUser;

import java.util.List;

public interface UsersRepository {
    ChatUser addUser(ChatUser user);

    ChatUser find(String adress);

    List<ChatUser> findAll();

}
