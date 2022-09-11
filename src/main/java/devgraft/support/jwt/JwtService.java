package devgraft.support.jwt;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(final JwtGenerateRequest request);
    boolean verifyToken(final String token);
    String getAud(final String token);
    String getSub(final String token);
    Claims getBody(final String token);
}
