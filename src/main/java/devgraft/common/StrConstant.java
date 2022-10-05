package devgraft.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StrConstant {
    public static final String SIGN_UP_KEY_PAIR = "up_key_pair";
    public static final String SIGN_IN_KEY_PAIR = "in_key_pair";
    public static final String HEADER_TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_ACCESS_TOKEN_SYNTAX = "ACCESS-TOKEN";
    public static final String COOKIE_REFRESH_TOKEN_SYNTAX = "REFRESH-TOKEN";
}
