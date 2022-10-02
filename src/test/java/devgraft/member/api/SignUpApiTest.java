package devgraft.member.api;

import devgraft.member.app.EncryptedSignUpRequest;
import devgraft.member.app.EncryptedSignUpRequestFixture;
import devgraft.member.app.SignUpService;
import devgraft.support.crypto.KeyPairFixture;
import devgraft.support.mapper.ObjectMapperTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.KeyPair;
import java.util.Base64;

import static devgraft.common.StrConstant.SIGN_UP_KEY_PAIR;
import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignUpApiTest extends ObjectMapperTest {
    private MockMvc mockMvc;
    private SignUpService mockSignUpService;

    @BeforeEach
    void setUp() {
        mockSignUpService = mock(SignUpService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new SignUpApi(mockSignUpService))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("회원가입 공개키 요청 결과")
    @Test
    void getPubKey_returnValue() throws Exception {
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        given(mockSignUpService.generatedSignUpCode()).willReturn(givenKeyPair);

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/sign-code"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(Base64.getEncoder().encodeToString(givenKeyPair.getPublic().getEncoded()))));
    }

    @DisplayName("회원가입 결과 HttpStatus.Created")
    @Test
    void signUp_returnCreatedHttpStatus() throws Exception {
        final EncryptedSignUpRequest givenRequest = EncryptedSignUpRequestFixture.anRequest().build();

        requestSignUp(getMockHttpSession(), givenRequest)
                .andExpect(status().isCreated());
    }

    @DisplayName("회원가입 요청 전 공개키 발급이 되지 않아 예외처리")
    @Test
    void signUp_throw_NotIssuedPublicKeyException() throws Exception {
        final EncryptedSignUpRequest givenRequest = EncryptedSignUpRequestFixture.anRequest().build();

        requestSignUp(new MockHttpSession(), givenRequest)
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotIssuedPublicKeyException))
        ;
    }

    @DisplayName("회원가입 요청문(암호) 서비스 전달")
    @Test
    void signUp_passesEncryptedSignUpRequestToSinUpService() throws Exception {
        final EncryptedSignUpRequest givenRequest = EncryptedSignUpRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();

        requestSignUp(getMockHttpSession(givenKeyPair), givenRequest);

        verify(mockSignUpService, times(1)).signUp(refEq(givenRequest), eq(givenKeyPair));
    }

    @DisplayName("회원가입 결과")
    @Test
    void signUp_returnValue() throws Exception {
        final EncryptedSignUpRequest givenRequest = EncryptedSignUpRequestFixture.anRequest().build();

        requestSignUp(getMockHttpSession(), givenRequest)
                .andExpect(jsonPath("$", equalTo("Success")))
        ;
    }

    private ResultActions requestSignUp(final MockHttpSession session, final EncryptedSignUpRequest givenRequest) throws Exception {
        return mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(givenRequest))
                .session(session));
    }

    private MockHttpSession getMockHttpSession() {
        final MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(SIGN_UP_KEY_PAIR, KeyPairFixture.anKeyPair());
        return mockHttpSession;
    }

    private MockHttpSession getMockHttpSession(final KeyPair keyPair) {
        final MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(SIGN_UP_KEY_PAIR, keyPair);
        return mockHttpSession;
    }
}