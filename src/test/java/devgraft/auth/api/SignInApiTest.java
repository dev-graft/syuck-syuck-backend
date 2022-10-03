package devgraft.auth.api;

import devgraft.auth.app.EncryptedSignInRequest;
import devgraft.auth.app.EncryptedSignInRequestFixture;
import devgraft.auth.app.SignInService;
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

import static devgraft.common.StrConstant.SIGN_IN_KEY_PAIR;
import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.AUTH_URL_PREFIX;
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

class SignInApiTest extends ObjectMapperTest {
    private MockMvc mockMvc;
    private SignInService mockSignInService;

    @BeforeEach
    void setUp() {
        mockSignInService = mock(SignInService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new SignInApi(mockSignInService))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("로그인 공개키 요청 결과")
    @Test
    void getSignInCode_returnValue() throws Exception {
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        given(mockSignInService.generatedSignCode()).willReturn(givenKeyPair);

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + AUTH_URL_PREFIX + "/sign-code"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(Base64.getEncoder().encodeToString(givenKeyPair.getPublic().getEncoded()))));
    }

    @DisplayName("로그인 요청 status-ok")
    @Test
    void signIn_return_OkHttpStatus() throws Exception {
        final EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();

        requestSignUp(getMockHttpSession(), givenRequest)
                .andExpect(status().isOk());
    }

    @DisplayName("로그인 요청 전 공개키 발급이 되지 않아 예외처리")
    @Test
    void signIn_throw_NotIssuedSignInCodeException() throws Exception {
        final EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();

        requestSignUp(new MockHttpSession(), givenRequest)
                .andExpect(status().is4xxClientError())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotIssuedSignInCodeException))
        ;
    }

    @DisplayName("로그인 요청문(암호) 로그인 서비스 전달")
    @Test
    void signIn_passes_EncryptedSignInRequestToSignInService() throws Exception {
        final EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();

        requestSignUp(getMockHttpSession(givenKeyPair), givenRequest);

        verify(mockSignInService, times(1)).signIn(refEq(givenRequest), eq(givenKeyPair));
    }

    private ResultActions requestSignUp(final MockHttpSession session, EncryptedSignInRequest givenRequest) throws Exception {
        return mockMvc.perform(post(API_PREFIX + VERSION_1_PREFIX + AUTH_URL_PREFIX +"/sign-in")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getObjectMapper().writeValueAsString(givenRequest))
                .session(session));
    }

    private MockHttpSession getMockHttpSession() {
        final MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(SIGN_IN_KEY_PAIR, KeyPairFixture.anKeyPair());
        return mockHttpSession;
    }

    private MockHttpSession getMockHttpSession(final KeyPair keyPair) {
        final MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(SIGN_IN_KEY_PAIR, keyPair);
        return mockHttpSession;
    }
}