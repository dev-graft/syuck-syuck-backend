package devgraft.module.member.infra;

import devgraft.module.member.domain.MemberCryptService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

class MemberCryptServiceImplTest {
    MemberCryptService memberCryptService;

    @BeforeEach
    void setUp() {
        memberCryptService = new MemberCryptServiceImpl();
    }

    @DisplayName("회원정보 암호화 키 생성")
    @Test
    void generatedCryptKey() {
        final KeyPair result = memberCryptService.generatedCryptKey();

        Assertions.assertThat(result).isNotNull();
    }
}