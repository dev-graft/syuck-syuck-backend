package devgraft.member.domain;

public class MemberFixture {

    public static Member.MemberBuilder anMember() {
        return Member.builder()
                .id(MemberId.from("loginId"))
                .password(Password.from("password"))
                .profileImage("profileImage")
                .stateMessage("stateMessage")
                .status(MemberStatus.N);
    }
}
