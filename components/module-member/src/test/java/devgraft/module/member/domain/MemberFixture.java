package devgraft.module.member.domain;

public class MemberFixture {
    public static Member.MemberBuilder anMember() {
        return Member.builder()
                .id(MemberId.from("loginId"))
                .password(Password.from("password"))
                .nickname("nickname")
                .profileImage("profileImage")
                .stateMessage("stateMessage")
                .status(MemberStatus.N);
    }
}
