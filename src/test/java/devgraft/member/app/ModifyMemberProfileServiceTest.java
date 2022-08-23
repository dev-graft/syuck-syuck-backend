package devgraft.member.app;

import devgraft.member.domain.SpyMemberRepository;
import devgraft.member.exception.NoMemberException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class ModifyMemberProfileServiceTest {
    private SpyMemberRepository spyMemberRepository;
    private ModifyMemberProfileService modifyMemberProfileService;

    @BeforeEach
    void setUp() {
        spyMemberRepository = Mockito.spy(SpyMemberRepository.class);
        modifyMemberProfileService = new ModifyMemberProfileService(spyMemberRepository);
    }

    @DisplayName("수정 대상 회원이 없을 경우 에러(삭제 상태도 판별)")
    @Test
    void notExistsHasError() {
        given(spyMemberRepository.findById(any())).willReturn(Optional.empty());

        final NoMemberException noMemberException = Assertions.catchThrowableOfType(
                () -> modifyMemberProfileService.modifyMemberProfile(ModifyMemberProfileRequest.builder().build(), 1L),
                NoMemberException.class);

        assertThat(noMemberException).isNotNull();
    }

    // 널이 아닌 값만 정규식 검사 후 결과에 따라 에러
    @DisplayName("수정 요청 값의 검증이 실패했을 경우 에러")
    @Test
    void validatedHasError() {

    }
}