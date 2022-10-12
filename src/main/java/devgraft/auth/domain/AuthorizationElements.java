package devgraft.auth.domain;

public interface AuthorizationElements {
    String getAccessToken();
    String getRefreshToken();
}
