package devgraft.follow.domain;

public class FollowFixture {

    public static Follow.FollowBuilder anFollow() {
        return Follow.builder()
                .memberId("memberId")
                .followingMemberId("following");
    }
}
