package devgraft.docs.friend;

import devgraft.auth.api.AuthCodeFilter;
import devgraft.auth.domain.AccessStatusType;
import devgraft.auth.query.AuthSessionData;
import devgraft.auth.query.AuthSessionDataDao;
import devgraft.common.credential.MemberCredentials;
import devgraft.common.credential.MemberCredentialsResolver;
import devgraft.friend.api.FriendQueryApi;
import devgraft.friend.query.FriendRelationData;
import devgraft.friend.query.FriendRelationDataDao;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.support.restdocs.AbstractApiDocTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import javax.servlet.http.Cookie;
import java.util.List;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FRIEND_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(FriendQueryApi.class)
public class FriendQueryApiDoc extends AbstractApiDocTest {
    @MockBean
    protected AuthCodeFilter authCodeFilter;
    @MockBean
    protected MemberCredentialsResolver memberCredentialsResolver;
    @MockBean
    private FriendRelationDataDao friendRelationDataDao;
    @MockBean
    private MemberDataDao memberDataDao;
    @MockBean
    private AuthSessionDataDao authSessionDataDao;
    private static final String DEFAULT_PROFILE_IMAGE = "https://www.gravatar.com/avatar/00000000000000000000000000000000?d=identicon&f=y";

    @DisplayName("?????? ?????? ??????")
    @Test
    void searchFriendList() throws Exception {
        given(memberCredentialsResolver.supportsParameter(any())).willReturn(true);
        given(memberCredentialsResolver.resolveArgument(any(), any(), any(), any())).willReturn(MemberCredentials.builder().memberId("qwerty123").build());
        given(friendRelationDataDao.findAll(any(), any())).willReturn(new PageImpl<>(List.of(
                FriendRelationData.builder().id(1L).sender("qwerty123").receiver("qwe123").areFriends(true).build(),
                FriendRelationData.builder().id(2L).sender("qwerty123").receiver("rty456").areFriends(true).build(),
                FriendRelationData.builder().id(3L).sender("qwerty123").receiver("tom12345").areFriends(true).build(),
                FriendRelationData.builder().id(4L).sender("qwerty123").receiver("jerry12345").areFriends(true).build()
        )));
        given(memberDataDao.findAll(any(), any())).willReturn(new PageImpl<>(List.of(
                MemberData.builder().memberId("qwe123").nickname("qwe").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("??????").build(),
                MemberData.builder().memberId("rty456").nickname("rty").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("???????????? ??????!").build(),
                MemberData.builder().memberId("tom12345").nickname("tom").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("??????").build(),
                MemberData.builder().memberId("jerry12345").nickname("jerry").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("??????").build()
        )));

        given(authSessionDataDao.findAll(any(), any())).willReturn(new PageImpl<>(List.of(
                AuthSessionData.builder().memberId("qwe123").connect(false).accessStatus(AccessStatusType.OFFLINE).build(),
                AuthSessionData.builder().memberId("rty456").connect(true).accessStatus(AccessStatusType.OTHER).build(),
                AuthSessionData.builder().memberId("tom12345").connect(true).accessStatus(AccessStatusType.EMPTY).build(),
                AuthSessionData.builder().memberId("jerry12345").connect(true).accessStatus(AccessStatusType.ONLINE).build()
        )));

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX)
//                        .principal()
                        .param("page", "0")
                        .header("ACCESS-TOKEN", "accessToken")
                        .cookie(new Cookie("REFRESH-TOKEN", "refreshToken")))
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("page").optional().description("?????????(default=0)")
                        ),
                        responseFields.and(
                                fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER).description("?????? ?????? ??? ?????????)"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("?????? ?????? ??? ??????"),
                                fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                fieldWithPath("data.values").type(JsonFieldType.ARRAY).description("????????? ?????? ??????"),
                                fieldWithPath("data.values[].frId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                fieldWithPath("data.values[].loginId").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.values[].nickname").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.values[].profileImage").type(JsonFieldType.STRING).description("????????? ?????????"),
                                fieldWithPath("data.values[].stateMessage").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.values[].accessStatusCode").type(JsonFieldType.STRING).description("?????? ?????? ?????? Code(OFFLINE, OTHER, EMPTY, ONLINE)"),
                                fieldWithPath("data.values[].accessStatusLabel").type(JsonFieldType.STRING).description("?????? ?????? ?????? Label(????????????, ?????? ?????? ???, ????????????, ?????????)"),
                                fieldWithPath("data.values[].accessStatusColor").type(JsonFieldType.STRING).description("?????? ?????? ?????? Color(gray, red, yellow, green)")
                        )
                ))
        ;
    }

    @DisplayName("?????? ????????? ?????? ?????? ??????")
    @Test
    void searchSendPostsFriendList() throws Exception {
        given(memberCredentialsResolver.supportsParameter(any())).willReturn(true);
        given(memberCredentialsResolver.resolveArgument(any(), any(), any(), any())).willReturn(MemberCredentials.builder().memberId("qwerty123").build());
        given(friendRelationDataDao.findAll(any(), any())).willReturn(new PageImpl<>(List.of(
                FriendRelationData.builder().id(1L).sender("qwerty123").receiver("qwe123").areFriends(false).build(),
                FriendRelationData.builder().id(2L).sender("qwerty123").receiver("rty456").areFriends(false).build(),
                FriendRelationData.builder().id(3L).sender("qwerty123").receiver("tom12345").areFriends(false).build(),
                FriendRelationData.builder().id(4L).sender("qwerty123").receiver("jerry12345").areFriends(false).build()
        )));
        given(memberDataDao.findAll(any(), any())).willReturn(new PageImpl<>(List.of(
                MemberData.builder().memberId("qwe123").nickname("qwe").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("??????").build(),
                MemberData.builder().memberId("rty456").nickname("rty").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("???????????? ??????!").build(),
                MemberData.builder().memberId("tom12345").nickname("tom").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("??????").build(),
                MemberData.builder().memberId("jerry12345").nickname("jerry").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("??????").build()
        )));

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/send")
                        .param("page", "0")
                        .header("ACCESS-TOKEN", "accessToken")
                        .cookie(new Cookie("REFRESH-TOKEN", "refreshToken")))
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("page").optional().description("?????????(default=0)")
                        ),
                        responseFields.and(
                                fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER).description("?????? ?????? ??? ?????????)"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("?????? ?????? ??? ??????"),
                                fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                fieldWithPath("data.values").type(JsonFieldType.ARRAY).description("????????? ?????? ??????"),
                                fieldWithPath("data.values[].frId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                fieldWithPath("data.values[].loginId").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.values[].nickname").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.values[].profileImage").type(JsonFieldType.STRING).description("????????? ?????????"),
                                fieldWithPath("data.values[].stateMessage").type(JsonFieldType.STRING).description("?????? ?????????")
                        )
                ))
        ;
    }

    @DisplayName("?????? ????????? ?????? ?????? ??????")
    @Test
    void searchReceivePostsFriendList() throws Exception {
        given(memberCredentialsResolver.supportsParameter(any())).willReturn(true);
        given(memberCredentialsResolver.resolveArgument(any(), any(), any(), any())).willReturn(MemberCredentials.builder().memberId("qwerty123").build());
        given(friendRelationDataDao.findAll(any(), any())).willReturn(new PageImpl<>(List.of(
                FriendRelationData.builder().id(1L).receiver("qwerty123").sender("qwe123").areFriends(false).build(),
                FriendRelationData.builder().id(2L).receiver("qwerty123").sender("rty456").areFriends(false).build(),
                FriendRelationData.builder().id(3L).receiver("qwerty123").sender("tom12345").areFriends(false).build(),
                FriendRelationData.builder().id(4L).receiver("qwerty123").sender("jerry12345").areFriends(false).build()
        )));
        given(memberDataDao.findAll(any(), any())).willReturn(new PageImpl<>(List.of(
                MemberData.builder().memberId("qwe123").nickname("qwe").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("??????").build(),
                MemberData.builder().memberId("rty456").nickname("rty").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("???????????? ??????!").build(),
                MemberData.builder().memberId("tom12345").nickname("tom").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("??????").build(),
                MemberData.builder().memberId("jerry12345").nickname("jerry").profileImage("DEFAULT_PROFILE_IMAGE").stateMessage("??????").build()
        )));

        mockMvc.perform(get(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/receive")
                        .param("page", "0")
                        .header("ACCESS-TOKEN", "accessToken")
                        .cookie(new Cookie("REFRESH-TOKEN", "refreshToken")))
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("page").optional().description("?????????(default=0)")
                        ),
                        responseFields.and(
                                fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER).description("?????? ?????? ??? ?????????)"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("?????? ?????? ??? ??????"),
                                fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                                fieldWithPath("data.values").type(JsonFieldType.ARRAY).description("????????? ?????? ??????"),
                                fieldWithPath("data.values[].frId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                fieldWithPath("data.values[].loginId").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.values[].nickname").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.values[].profileImage").type(JsonFieldType.STRING).description("????????? ?????????"),
                                fieldWithPath("data.values[].stateMessage").type(JsonFieldType.STRING).description("?????? ?????????")
                        )
                ))
        ;
    }
}
