package devgraft.member.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ModifyMemberProfileServiceTest {
    ModifyMemberProfileService modifyMemberProfileService = new ModifyMemberProfileService();

    // 요청자의 id에 해당하는 사용자가 존재하는지 검사 후 에러 -> 회원이 삭제 상태인지도 확인이 필요하다
    // 널이 아닌 값만 정규식 검사 후 결과에 따라 에러


    @DisplayName("수정 대상 회원이 없을 경우 에러")
    @Test
    void notExistsHasError() {

    }

    @DisplayName("수정 요청 값의 검증이 실패했을 경우 에러")
    @Test
    void validatedHasError() {

    }
}