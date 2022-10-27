package devgraft.friend.domain;

public class FriendRelationFixture {
    public static FriendRelation.FriendRelationBuilder anFriendRelation() {
        return FriendRelation.builder()
                .areFriends(true)
                .sender("sender")
                .receiver("receiver");
    }
}
