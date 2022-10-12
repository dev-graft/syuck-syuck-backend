package devgraft.docs.member;

import devgraft.auth.api.AuthCodeFilter;
import devgraft.member.api.MemberQueryApi;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.support.restdocs.AbstractApiDocTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.Optional;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({MemberQueryApi.class})
public class MemberQueryApiDoc extends AbstractApiDocTest {
    @MockBean
    private AuthCodeFilter authCodeFilter;
    @MockBean
    private MemberDataDao memberDataDao;

    @DisplayName("아이디 존재 여부 확인 요청")
    @Test
    void isExistsLoginId() throws Exception {
        given(memberDataDao.findOne(any())).willReturn(Optional.of(MemberData.builder().build()));

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/exists")
                        .param("loginId", "qwerty123"))
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("loginId").description("회원 아이디")
                        ),
                        responseFields.and(
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("아이디 존재 여부 결과(True=존재/False=존재안함)")
                        )
                ));
    }

    @DisplayName("회원 프로필 요청")
    @Test
    void getMemberProfile() throws Exception {
        given(memberDataDao.findOne(any())).willReturn(Optional.of(MemberData.builder()
                .memberId("qwerty123")
                .nickname("nic")
                .profileImage("https://secure.gravatar.com/avatar/835628379d78a39af54f1c5ebfc050b4?s=800&d=identicon")
                .stateMessage("좋은 날이에요!!")
                .build()));

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/profile")
                        .param("loginId", "qwerty123"))
                .andExpect(status().isOk())
                .andDo(document.document(
                                requestParameters(
                                        parameterWithName("loginId").description("회원 아이디")
                                ),
                                responseFields.and(
                                        fieldWithPath("data.loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("별명"),
                                        fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                        fieldWithPath("data.stateMessage").type(JsonFieldType.STRING).description("상태 메세지")
                                )
                        )
                );
    }

    @DisplayName("회원명 기준 목록 검색")
    @Test
    void searchNickname() throws Exception {
        final Page<MemberData> givenPages = new PageImpl<>(List.of(MemberData.builder()
                .memberId("qwerty123")
                .nickname("QWERTY")
                .profileImage("https://secure.gravatar.com/avatar/835628379d78a39af54f1c5ebfc050b4?s=800&d=identicon")
                .stateMessage("좋은 날이에요!!")
                .build()));
        given(memberDataDao.findAll(any(), any())).willReturn(givenPages);

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/nickname")
                        .param("keyword", "Q")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andDo(document.document(
                                requestParameters(
                                        parameterWithName("keyword").description("검색 키워드"),
                                        parameterWithName("page").description("페이지 (default=0)")
                                ),
                                responseFields.and(
                                        fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("검색 페이지"),
                                        fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("검색 결과 크기"),
                                        fieldWithPath("data.maxSize").type(JsonFieldType.NUMBER).description("검색 결과 최대 크기(서버관리)"),
                                        fieldWithPath("data.members").type(JsonFieldType.ARRAY).description("검색된 회원 목록"),
                                        fieldWithPath("data.members[].loginId").type(JsonFieldType.STRING).description("아이디"),
                                        fieldWithPath("data.members[].nickname").type(JsonFieldType.STRING).description("별명"),
                                        fieldWithPath("data.members[].profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL")
                                )
                        )
                );
    }

    @DisplayName("회원 아이디 목록 검색")
    @Test
    void searchLoginId() throws Exception {
        final Page<MemberData> givenPages = new PageImpl<>(List.of(MemberData.builder()
                .memberId("qwerty123")
                .nickname("QWERTY")
                .profileImage("https://secure.gravatar.com/avatar/835628379d78a39af54f1c5ebfc050b4?s=800&d=identicon")
                .stateMessage("좋은 날이에요!!")
                .build()));
        given(memberDataDao.findAll(any(), any())).willReturn(givenPages);

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/login-id")
                        .param("keyword", "Q")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andDo(document.document(
                                requestParameters(
                                        parameterWithName("keyword").description("검색 키워드"),
                                        parameterWithName("page").description("페이지 (default=0)")
                                ),
                                responseFields.and(
                                        fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("검색 페이지"),
                                        fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("검색 결과 크기"),
                                        fieldWithPath("data.maxSize").type(JsonFieldType.NUMBER).description("검색 결과 최대 크기(서버관리)"),
                                        fieldWithPath("data.members").type(JsonFieldType.ARRAY).description("검색된 회원 목록"),
                                        fieldWithPath("data.members[].loginId").type(JsonFieldType.STRING).description("아이디"),
                                        fieldWithPath("data.members[].nickname").type(JsonFieldType.STRING).description("별명"),
                                        fieldWithPath("data.members[].profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL")
                                )
                        )
                );
    }
}
