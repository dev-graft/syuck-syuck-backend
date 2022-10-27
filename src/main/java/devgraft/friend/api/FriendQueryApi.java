package devgraft.friend.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import devgraft.auth.domain.AccessStatusType;
import devgraft.auth.query.AuthSessionData;
import devgraft.auth.query.AuthSessionDataDao;
import devgraft.auth.query.AuthSessionDataSpec;
import devgraft.common.SearchResult;
import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.friend.query.FriendRelationData;
import devgraft.friend.query.FriendRelationDataDao;
import devgraft.friend.query.FriendRelationDataSpec;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.member.query.MemberDataSpec;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FRIEND_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class FriendQueryApi {
    private final FriendRelationDataDao friendRelationDataDao;
    private final MemberDataDao memberDataDao;
    private final AuthSessionDataDao authSessionDataDao;

    // GET 친구 목록 조회 (Self)
    @GetMapping(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX)
    public SearchResult<FriendDetails> searchFriendList(@Credentials final MemberCredentials memberCredentials, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        // 자신과 관련된 아이디 전부 갖고 옴 (친구상태인 내용)
        final Page<FriendRelationData> friendRelationDataDaoAll = friendRelationDataDao.findAll(FriendRelationDataSpec.areFriends(true)
                        .and(FriendRelationDataSpec.senderEquals(memberCredentials.getMemberId())
                                .or(FriendRelationDataSpec.receiverEquals(memberCredentials.getMemberId()))),
                Pageable.unpaged());

        final List<FriendRelationMemberInfo> friendMemberInfos = friendRelationDataDaoAll.stream()
                .map(friendRelationData -> FriendRelationMemberInfo.builder()
                        .fId(friendRelationData.getId())
                        .memberId(friendRelationData.getSender().equals(memberCredentials.getMemberId()) ? friendRelationData.getReceiver() : friendRelationData.getSender())
                        .build())
                .collect(Collectors.toList());

        List<String> friendMemberIds = friendMemberInfos.stream().map(FriendRelationMemberInfo::getMemberId).collect(Collectors.toUnmodifiableList());

        // 아이디 대상들 Member 정보 갖고 옴,
        final Page<MemberData> memberDataDaoAll = memberDataDao.findAll(MemberDataSpec.memberIdContains(friendMemberIds).and(MemberDataSpec.normalEquals()), PageRequest.of(page, 20));
        final List<String> memberIds = memberDataDaoAll.stream().map(MemberData::getMemberId).collect(Collectors.toUnmodifiableList());
        // Member 정보 기반 Auth에서 정보 갖고 옴
        final Page<AuthSessionData> authSessionDataDaoAll = authSessionDataDao.findAll(AuthSessionDataSpec.memberIdContains(memberIds).and(AuthSessionDataSpec.notBlock()), Pageable.unpaged());
        // 병합해서 반환함
        final List<FriendDetails> values = memberDataDaoAll.stream()
                .map(memberData -> FriendDetails.builder()
                        .fId(friendMemberInfos.stream().filter(info -> info.getMemberId().equals(memberData.getMemberId())).map(FriendRelationMemberInfo::getFId).findFirst().orElse(-1L))
                        .memberId(memberData.getMemberId())
                        .nickname(memberData.getNickname())
                        .profileImage(memberData.getProfileImage())
                        .stateMessage(memberData.getStateMessage())
                        .accessStatusType(authSessionDataDaoAll.stream()
                                .filter(authSessionData ->
                                        authSessionData.getMemberId().equals(memberData.getMemberId()) &&
                                                authSessionData.isConnect())
                                .map(AuthSessionData::getAccessStatus)
                                .min(Comparator.comparing(AccessStatusType::getCodeNum))
                                .orElse(AccessStatusType.OFFLINE))
                        .build())
                .collect(Collectors.toUnmodifiableList());

        return SearchResult.<FriendDetails>builder()
                .totalPage(memberDataDaoAll.getTotalPages())
                .totalElements(memberDataDaoAll.getTotalElements())
                .page(page)
                .size(memberIds.size())
                .values(values)
                .build();
    }

    // GET 친구 요청 목록 조회 (Self) / 내가한 친구요청 목록만
    @GetMapping(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/send")
    public SearchResult<FriendDetails> searchSendPostsFriendList(@Credentials final MemberCredentials memberCredentials, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        final Page<FriendRelationData> friendRelationDataDaoAll = friendRelationDataDao.findAll(FriendRelationDataSpec.areFriends(false).and(FriendRelationDataSpec.senderEquals(memberCredentials.getMemberId())),
                Pageable.unpaged());
        final List<String> receiverIds = friendRelationDataDaoAll.stream().map(FriendRelationData::getReceiver).collect(Collectors.toUnmodifiableList());

        final Page<MemberData> memberDataDaoAll = memberDataDao.findAll(MemberDataSpec.memberIdContains(receiverIds).and(MemberDataSpec.normalEquals()), PageRequest.of(page, 20));

        final List<FriendDetails> values = memberDataDaoAll.stream()
                .map(memberData -> FriendDetails.builder()
                        .fId(friendRelationDataDaoAll.stream().filter(data -> data.getReceiver().equals(memberData.getMemberId())).map(FriendRelationData::getId).findFirst().orElse(-1L))
                        .memberId(memberData.getMemberId())
                        .nickname(memberData.getNickname())
                        .profileImage(memberData.getProfileImage())
                        .stateMessage(memberData.getStateMessage())
                        .build())
                .collect(Collectors.toUnmodifiableList());

        return SearchResult.<FriendDetails>builder()
                .totalPage(memberDataDaoAll.getTotalPages())
                .totalElements(memberDataDaoAll.getTotalElements())
                .page(page)
                .size(values.size())
                .values(values)
                .build();
    }
    // GET 친구 요청 목록 조회 (Self) / 나한테 온 친구요청 목록만
    @GetMapping(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/receive")
    public SearchResult<FriendDetails> searchReceivePostsFriendList(@Credentials final MemberCredentials memberCredentials, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        final Page<FriendRelationData> friendRelationDataDaoAll = friendRelationDataDao.findAll(FriendRelationDataSpec.areFriends(false).and(FriendRelationDataSpec.receiverEquals(memberCredentials.getMemberId())),
                Pageable.unpaged());
        final List<String> senderIds = friendRelationDataDaoAll.stream().map(FriendRelationData::getSender).collect(Collectors.toUnmodifiableList());

        final Page<MemberData> memberDataDaoAll = memberDataDao.findAll(MemberDataSpec.memberIdContains(senderIds).and(MemberDataSpec.normalEquals()), PageRequest.of(page, 20));

        final List<FriendDetails> values = memberDataDaoAll.stream()
                .map(memberData -> FriendDetails.builder()
                        .fId(friendRelationDataDaoAll.stream().filter(data -> data.getReceiver().equals(memberData.getMemberId())).map(FriendRelationData::getId).findFirst().orElse(-1L))
                        .memberId(memberData.getMemberId())
                        .nickname(memberData.getNickname())
                        .profileImage(memberData.getProfileImage())
                        .stateMessage(memberData.getStateMessage())
                        .build())
                .collect(Collectors.toUnmodifiableList());

        return SearchResult.<FriendDetails>builder()
                .totalPage(memberDataDaoAll.getTotalPages())
                .totalElements(memberDataDaoAll.getTotalElements())
                .page(page)
                .size(values.size())
                .values(values)
                .build();
    }

    @Builder
    @Getter
    public static class FriendRelationMemberInfo {
        private final Long fId;
        private final String memberId;
    }

    @Getter
    public static class FriendDetails {
        private final Long fId; // 친구관계 ID
        private final String memberId; // 친구 ID
        private final String nickname;
        private final String profileImage;
        private final String stateMessage;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String accessStatusCode;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String accessStatusLabel;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String accessStatusColor;

        @Builder(access = AccessLevel.PUBLIC)
        private FriendDetails(final Long fId, final String memberId, final String nickname, final String profileImage, final String stateMessage, final AccessStatusType accessStatusType) {
            this.fId = fId;
            this.memberId = memberId;
            this.nickname = nickname;
            this.profileImage = profileImage;
            this.stateMessage = stateMessage;

            this.accessStatusCode = null != accessStatusType ? accessStatusType.name() : null;
            this.accessStatusLabel = null != accessStatusType ? accessStatusType.getLabel() : null;
            this.accessStatusColor = null != accessStatusType ? accessStatusType.getColor() : null;
        }
    }
}
