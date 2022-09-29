package devgraft.module.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConstant {
    public static final String MEMBERSHIP_FAILURE = "회원가입 요청이 실패하였습니다.";
    public static final String LOGIN_FAILURE = "로그인 요청이 실패하였습니다.";
    public static final String PASSWORD_NOT_MATCH = "비밀번호가 일치하지 않습니다.";
    public static final String ALREADY_EXISTS_MEMBER_ID = "이미 존재하는 아이디입니다.";
    public static final String NOT_FOUND_MEMBER = "요청한 회원 정보가 존재하지 않습니다.";
    public static final String NOT_ISSUED_PUBLIC_KEY = "공개키 발급을 우선 진행해주세요.";
    public static final String UNAUTHENTICATED_PUBLIC_KEY = "공개키가 올바르지 않습니다.";
}
