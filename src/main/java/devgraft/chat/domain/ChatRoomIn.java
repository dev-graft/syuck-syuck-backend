package devgraft.chat.domain;

/**채팅방에 포함된 정보*/
public class ChatRoomIn {
    String chatRoomId;
    String memberId;
    String joinAt;
    String status; // 참여 상태 { normal, leave }
}
