package yoon_hyojin.hackmate.exceptions.chat;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.exceptions.common.DomainException;

public class ChatNoticeNotFoundException extends DomainException {
    public ChatNoticeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "CHAT_NOTICE_NOT_FOUND", "존재하지 않는 채팅 공지입니다.");
    }
}
