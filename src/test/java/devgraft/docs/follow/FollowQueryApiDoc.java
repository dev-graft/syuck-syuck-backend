package devgraft.docs.follow;

import devgraft.auth.api.AuthCodeFilter;
import devgraft.follow.api.FollowQueryApi;
import devgraft.follow.query.FollowDataDao;
import devgraft.follow.query.FollowDataFixture;
import devgraft.member.domain.FindMemberIdsService;
import devgraft.support.restdocs.AbstractApiDocTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(FollowQueryApi.class)
public class FollowQueryApiDoc extends AbstractApiDocTest {
    @MockBean
    private AuthCodeFilter authCodeFilter;

    @MockBean
    private FollowDataDao followDataDao;

    @MockBean
    private FindMemberIdsService findMemberIdsService;

    @DisplayName("팔로워 목록 조회(Target을 팔로우한 회원 목록 조회)")
    @Test
    void searchFollower() throws Exception {
        given(followDataDao.findAll(any(), any())).willReturn(new PageImpl<>(List.of(FollowDataFixture.anFollowData().build())));
        given(findMemberIdsService.findMembers(any())).willReturn(List.of(new FindMemberIdsService.FindMemberResult("tom12345", "Tom", "https://w7.pngwing.com/pngs/117/60/png-transparent-kid-tom-illustration-tom-cat-jerry-mouse-tom-and-jerry-cartoon-jerry-mouse-s-white-mammal-cat-like-mammal.png", "냐옹")));

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX + "/follower")
                .param("target", "qwerty123")
                .param("page", "0"))
                .andDo(document.document(
                                requestParameters(
                                        parameterWithName("target").description("검색 키워드"),
                                        parameterWithName("page").description("페이지 (default=0)")
                                ),
                                responseFields.and(
                                        fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER).description("검색 결과 총 페이지)"),
                                        fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("검색 결과 총 요소"),
                                        fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("검색 페이지"),
                                        fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("검색 결과 크기"),
                                        fieldWithPath("data.values").type(JsonFieldType.ARRAY).description("검색된 회원 목록"),
                                        fieldWithPath("data.values[].loginId").type(JsonFieldType.STRING).description("아이디"),
                                        fieldWithPath("data.values[].nickname").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.values[].profileImage").type(JsonFieldType.STRING).description("프로필 이미지")
                                )
                        )
                );
    }

    @DisplayName("팔로잉 목록 조회(Target이 팔로우한 회원 목록 조회)")
    @Test
    void searchFollowing() throws Exception {
        given(followDataDao.findAll(any(), any())).willReturn(new PageImpl<>(List.of(FollowDataFixture.anFollowData().build())));
        given(findMemberIdsService.findMembers(any())).willReturn(List.of(new FindMemberIdsService.FindMemberResult("tom12345", "Tom", "https://w7.pngwing.com/pngs/117/60/png-transparent-kid-tom-illustration-tom-cat-jerry-mouse-tom-and-jerry-cartoon-jerry-mouse-s-white-mammal-cat-like-mammal.png", "냐옹")));

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX + "/following")
                        .param("target", "qwerty123")
                        .param("page", "0"))
                .andDo(document.document(
                                requestParameters(
                                        parameterWithName("target").description("검색 키워드"),
                                        parameterWithName("page").description("페이지 (default=0)")
                                ),
                                responseFields.and(
                                        fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER).description("검색 결과 총 페이지)"),
                                        fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("검색 결과 총 요소"),
                                        fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("검색 페이지"),
                                        fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("검색 결과 크기"),
                                        fieldWithPath("data.values").type(JsonFieldType.ARRAY).description("검색된 회원 목록"),
                                        fieldWithPath("data.values[].loginId").type(JsonFieldType.STRING).description("아이디"),
                                        fieldWithPath("data.values[].nickname").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.values[].profileImage").type(JsonFieldType.STRING).description("프로필 이미지")
                                )
                        )
                );
    }
}
