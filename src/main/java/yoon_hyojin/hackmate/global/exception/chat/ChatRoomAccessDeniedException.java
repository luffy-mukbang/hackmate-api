package yoon_hyojin.hackmate.global.exception.chat;

import org.springframework.http.HttpStatus;
import yoon_hyojin.hackmate.global.exception.DomainException;

public class ChatRoomAccessDeniedException extends DomainException {
    public ChatRoomAccessDeniedException() {
        super(HttpStatus.FORBIDDEN, "CHAT_ROOM_ACCESS_DENIED", "해당 채팅방에 접근 권한이 없습니다.");
    }
}
