package com.hackmate.global.exception.chat;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class ChatRoomNotFoundException extends DomainException {
    public ChatRoomNotFoundException() {
        super(HttpStatus.NOT_FOUND, "CHAT_ROOM_NOT_FOUND", "존재하지 않는 채팅방입니다.");
    }
}
