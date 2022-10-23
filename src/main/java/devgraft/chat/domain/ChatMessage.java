package devgraft.chat.domain;

import java.time.LocalDateTime;

/**메세지*/
public class ChatMessage {
    String id;
    String chatRoomId;
    String writer;
    String message;
    LocalDateTime issuedAt;
}
