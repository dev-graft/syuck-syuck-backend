package devgraft.follow.domain;

public interface ExistsFollowTargetService {
    /**target: memberId와 동일*/
    boolean isExistsFollowTarget(final String target);
}
