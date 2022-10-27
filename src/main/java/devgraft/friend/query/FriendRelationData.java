package devgraft.friend.query;

import devgraft.support.jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate(value = false)
@Table(name = "friendRelation")
@Getter
@Entity
public class FriendRelationData extends BaseEntity {
    @Id
    private Long id;
    private boolean areFriends;
    private String sender; // 친구 요청자
    private String receiver; // 친구 대상
}
