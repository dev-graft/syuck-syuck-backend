package devgraft.follow.query;

public class FollowDataFixture {

    public static FollowData.FollowDataBuilder anFollowData() {
        return FollowData.builder()
                .id(0L)
                .memberId("qwerty123")
                .followingMemberId("dede123");
    }
}
