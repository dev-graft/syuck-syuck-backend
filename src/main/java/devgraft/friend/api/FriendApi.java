package devgraft.friend.api;

public class FriendApi {
    public void postFriend() {}
    // TODO GET 친구 목록 조회 (Self)
    // TODO GET 친구 요청 목록 조회 (Self) / 내가한 친구요청 목록만
    // TODO GET 친구 요청 목록 조회 (Self) / 나한테 온 친구요청 목록만

    // POST 친구 요청 (Self) /friends/posts
    // PUT 친구 요청 승인 (Self) /friends/posts/accept
    // PUT 친구 요청 취소 (Self) /friends/posts/cancel
    // PUT 친구 요청 거절 (Self) /friends/posts/refuse

    // TODO DELETE 친구 취소 (Self) /friends/cancel
    // 요청과 승인이 분리될 필요가 있는지 검토 필요
}

// A가 B한테 친구 요청을 했음

// B님에게 친구요청을 보냈습니다.

// B가 친구 요청 승인함

// A 타임라인
// B님에게 친구요청을 보냈습니다.
// B님이 친구 요청을 수락했습니다.

// B 타임라인
// A님에게 친구요청이 왔습니다.
// A님의 친구요청을 수락했습니다.

