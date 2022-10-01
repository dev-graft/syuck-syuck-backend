package devgraft.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class URLPrefix {
    public static final String API_PREFIX = "/api";
    public static final String VERSION_1_PREFIX = "/v1";
    public static final String MEMBER_URL_PREFIX = "/members";
}
