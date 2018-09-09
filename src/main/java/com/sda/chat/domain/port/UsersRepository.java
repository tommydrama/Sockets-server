package com.sda.chat.domain.port;

import com.sda.chat.domain.model.ChatUser;

import java.util.List;

public interface UsersRepository {
    ChatUser addUser(ChatUser user);

    ChatUser findByAdress(String adress);

    ChatUser findByName(String name);

    List<ChatUser> findAll();


}
