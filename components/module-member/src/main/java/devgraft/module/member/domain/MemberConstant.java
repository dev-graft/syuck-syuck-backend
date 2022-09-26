package devgraft.module.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConstant {
    public static final String MEMBERSHIP_FAILURE = "회원가입 요청이 실패하였습니다.";
    public static final String ALREADY_EXISTS_MEMBER_ID = "이미 존재하는 아이디입니다.";
}
