package devgraft.module.member.app;

import devgraft.module.member.domain.MemberCryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@RequiredArgsConstructor
@Service
public class GenerateCryptoKeyService {
    private final MemberCryptService memberCryptService;
    public KeyPair process() {
        return memberCryptService.generatedCryptKey();
    }
}
