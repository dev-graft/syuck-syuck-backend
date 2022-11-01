package devgraft.timeline.domain;

import devgraft.support.jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TimeLine")
@Getter
@Entity
public class TimeLine extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag;
    private String code;
    private String memberId;
    private String content;
}
