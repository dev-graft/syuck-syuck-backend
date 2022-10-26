package devgraft.friend.domain;

import devgraft.support.jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "friendRelation")
@Getter
@Entity
public class FriendRelation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private boolean areFriends;
    @Column(nullable = false)
    private String sender; // 친구 요청자
    @Column(nullable = false)
    private String receiver; // 친구 대상

    public static FriendRelation create(final String sender, final String receiver) {
        return builder()
                .sender(sender)
                .receiver(receiver)
                .areFriends(false)
                .build();
    }

    public void acceptFriendRequest() {
        this.areFriends = true;
    }

    public boolean compareSenderTo(final String target) {
        return sender.equals(target);
    }

    public boolean compareReceiverTo(final String target) {
        return receiver.equals(target);
    }
}
