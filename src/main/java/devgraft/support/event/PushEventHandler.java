package devgraft.support.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import devgraft.auth.query.AuthSessionDataDao;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.member.query.MemberDataSpec;
import devgraft.support.event.push.PushEventInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PushEventHandler {
    // 알람 허용 어부 DB도 추가되어야함
    private final AuthSessionDataDao authSessionDataDao;
    private final MemberDataDao memberDataDao;
    private final ObjectMapper objectMapper;

    @EventListener(PushEventInterface.class)
    public void handle(final PushEventInterface event) throws JsonProcessingException {
//        // 회원 정보 조회
//        final Optional<MemberData> memberDataOptional = memberDataDao.findOne(MemberDataSpec.loggedIdEquals(event.getMemberId()).and(MemberDataSpec.normalEquals()));
//        if (memberDataOptional.isEmpty()) return;
//        // 알람 허용 여부 확인
//        // fcm 토큰 얻음
//        // 메세지 전송
////        memberDataOptional.get().getNickname()
//        log.info("PushEvent: {}", memberDataOptional.get().getNickname() + event.getContent());
//        // DB 저장(전송 성공 여부까지)
    }
}
