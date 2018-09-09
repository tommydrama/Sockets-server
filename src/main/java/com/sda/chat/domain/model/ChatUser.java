package com.sda.chat.domain.model;

import java.util.Objects;

public class ChatUser {
    private String name;
    private String adress;

    public ChatUser(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatUser user = (ChatUser) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(adress, user.adress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, adress);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
