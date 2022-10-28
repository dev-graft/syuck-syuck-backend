package devgraft.support.event;

public interface PushEventInterface extends EventInterface {
//    Long getPushId();
//    String getFcmToken();
    String getMemberId();
//    String getMemberNickname();
//    String getMemberProfile();
    String getMessage();
}
