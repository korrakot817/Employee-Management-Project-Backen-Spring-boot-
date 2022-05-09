package com.Project.Backend.exception;

public class ChatException extends BaseException{
    public ChatException(String code) {
        super("Chat." + code);
    }

    public static ChatException accessDined(){
        return new ChatException("access.dined");

    }

}
