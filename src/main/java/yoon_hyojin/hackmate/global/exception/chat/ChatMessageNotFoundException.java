package yoon_hyojin.hackmate.global.exception.chat;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class ChatMessageNotFoundException extends DomainException {
    public ChatMessageNotFoundException() {
        super(HttpStatus.NOT_FOUND, "CHAT_MESSAGE_NOT_FOUND", "존재하지 않는 채팅 메시지입니다.");
    }
}
