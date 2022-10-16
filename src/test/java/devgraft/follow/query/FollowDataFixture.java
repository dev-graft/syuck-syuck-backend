package devgraft.follow.query;

public class FollowDataFixture {

    public static FollowData.FollowDataBuilder anFollowData() {
        return FollowData.builder()
                .id(0L)
                .followerId("qwerty123")
                .followingId("dede123");
    }
}
