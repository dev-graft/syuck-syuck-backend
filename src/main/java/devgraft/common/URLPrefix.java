package devgraft.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class URLPrefix {
    public static final String API_PREFIX = "/api";
    public static final String VERSION_1_PREFIX = "/v1";
    public static final String VERSION_2_PREFIX = "/v2";
    public static final String MEMBER_URL_PREFIX = "/members";
    public static final String AUTH_URL_PREFIX = "/auth";
    public static final String FOLLOW_URL_PREFIX = "/follow";
    public static final String UNFOLLOW_URL_PREFIX = "/unfollow";
    public static final String FRIEND_URL_PREFIX = "/friends";
}
