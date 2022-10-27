package devgraft.friend.domain;

/** 친구 요청 대상이 존재하는지 여부 확인 도메인 서비스 */
public interface ExistsFriendTargetService {
    boolean isExistsFriendTarget(final String target);
}
