package devgraft.follow.app;

public class AskFollowRequestFixture {
    public static AskFollowRequest.AskFollowRequestBuilder anRequest() {
        return AskFollowRequest.builder()
                .followerLoginId("followerLoginId");
    }
}
