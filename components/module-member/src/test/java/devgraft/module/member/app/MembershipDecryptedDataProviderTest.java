package devgraft.module.member.app;

import devgraft.module.member.domain.MemberCryptService;
import devgraft.module.member.domain.MembershipDecryptedData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MembershipDecryptedDataProviderTest {
    private MembershipDecryptedDataProvider membershipDecryptedDataProvider;
    private MemberCryptService memberCryptService;

    @BeforeEach
    void setUp() {
        memberCryptService = mock(MemberCryptService.class);
        membershipDecryptedDataProvider = new MembershipDecryptedDataProvider();
    }

    @DisplayName("회원가입 요청문 복호화 로직 테스트")
    @Test
    void generatedDecryptMembershipRequest() {
        final EncryptMembershipRequest givenRequest = new EncryptMembershipRequest("", "", "", "");
        final KeyPair givenKeyPair = new KeyPair(null, null);
        given(memberCryptService.decrypt(refEq(givenKeyPair), eq(givenRequest.getPassword()))).willReturn("decryptPassword");

        final MembershipDecryptedData result = membershipDecryptedDataProvider.create(memberCryptService, givenRequest, givenKeyPair);

        assertThat(result.getLoginId()).isEqualTo(givenRequest.getLoginId());
        assertThat(result.getPassword()).isEqualTo("decryptPassword");
        assertThat(result.getNickname()).isEqualTo(givenRequest.getNickname());
        assertThat(result.getProfileImage()).isEqualTo(givenRequest.getProfileImage());
    }
}