package devgraft.chat.api;

public class ChatRoomApi {

    // ChatRoom 생성
        // id, title, issuedMemberId(중요하진않음), pub, sub, createAt, updateAt -> chatroom uniq randomId
    // 자신이 참여한 ChatRoom 목록 조회
        // id -> title, members,  이건 고려해봐야함 >> lastIssuedMessage, lastIssuedMessageAt
    // ChatRoom 정보 상세 조회

    // ChatRoom 초대 ->
    /**
     * A사용자가 B를 초대했다면? B유저는 어떻게 A유저가 초대했는지를 아는가
     *
     * 1번 방법
     * | 주기적으로 자신이 참여한 채팅방 목록을 조회한다.
     * > 주기적이라는 기준이 모호하고, 기간이 길면 사용자에게 불편함이 생긴다.(채팅 소실 등)
     *
     * 2번 방법
     * | 사용자가 회원가입을 했을 시, 사용자 고유의 sub key를 발급.
     * | 사용자는 앱 실행 후 항시 subKey를 구독
     * | 다른 사용자에게 초대되었을 때 subKey를 기반으로 방에 초대되었음을 알린다.
     * > 사용자마다 키를 우후죽순 만들어도 되는지 확인 필요.
     * > 다중 클라이언트에서 동일한 subKey를 사용하면 편할 것 같다. / 이럴 경우 자신의 subKey를 조회하는 api가 필요하다. 모바일 homeInit과정에 호출하면 될듯
     * > 이렇게 해결하면 웹은 fcm 처리 필요 없이 해당 값으로 알람을 처리할 수 있겠다. 프로세스 검토가 필요함.
 듯   */
    // ChatRoom 초대 링크 생성

    // ChatRoom 참여(chatroom_in에 추가되고,
        // rId, memberId -> chatroom_in 에 memberId 추가 후 이벤트 발송
    // ChatRoom 탈퇴
        // rId, memberId -> chatroom_in 에 memberId 제거 후 이벤트 발송
}
