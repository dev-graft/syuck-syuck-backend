package devgraft.support.event.push;

import devgraft.support.event.EventInterface;

/** 푸쉬도 종류가 많은데, 푸쉬에 보여줘야할 사진 등은 어떻게 값을 줄지 고민 **/
public interface PushEventInterface extends EventInterface {
    /** push 대상(이력 저장용) */
    String getMemberId();
    /** push 메세지 title */
    String getTitle();
    /** push 메세지 내용 */
    String getContent();
}
