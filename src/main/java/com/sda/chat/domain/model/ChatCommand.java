package com.sda.chat.domain.model;

public enum ChatCommand {
    LIST_USERS("users"), SEND_MESSAGE("send");

    private String commandName;

    ChatCommand(String commandName) {
        this.commandName = commandName;
    }

    public static ChatCommand getByCommandName (String commandName) {
        switch (commandName){
            case "users":
                return ChatCommand.LIST_USERS;
            case "send":
                return ChatCommand.SEND_MESSAGE;
                default:
                    return null;
        }
    }
}
