package devgraft.docs.auth;

import devgraft.auth.api.AuthCodeFilter;
import devgraft.auth.api.AuthCodeIOUtils;
import devgraft.auth.api.AuthCodeIOUtils.AuthorizationCode;
import devgraft.auth.api.SignInRefreshApi;
import devgraft.auth.app.SignInRefreshService;
import devgraft.auth.app.SignInRefreshService.SignInRefreshResult;
import devgraft.docs.OldAbstractApiDocTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Optional;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.AUTH_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({SignInRefreshApi.class})
class AuthCodeRefreshApiDoc extends OldAbstractApiDocTest {
    @MockBean
    private AuthCodeFilter authCodeFilter;
    @MockBean
    private SignInRefreshService signInRefreshService;
    @MockBean
    private AuthCodeIOUtils authCodeIOUtils;

    @DisplayName("로그인 인가정보 갱신")
    @Test
    void refresh() throws Exception {
        given(authCodeIOUtils.exportAuthorizationCode(any())).willReturn(Optional.of(new AuthorizationCode("A", "B")));
        given(signInRefreshService.refresh(any())).willReturn(new SignInRefreshResult("A", "R"));

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + AUTH_URL_PREFIX + "/refresh"))
                .andDo(document.document(
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("읽을 필요 없는 값입니다.")
                        )
                ));
    }
}
