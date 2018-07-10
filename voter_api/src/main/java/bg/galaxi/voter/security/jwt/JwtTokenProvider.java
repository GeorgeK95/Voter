package bg.galaxi.voter.security.jwt;

import bg.galaxi.voter.security.user.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static bg.galaxi.voter.util.AppConstants.*;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value($_APP_JWT_SECRET)
    private String jwtSecret;

    @Value($_APP_JWT_EXPIRATION_IN_MS)
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error(INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException ex) {
            logger.error(INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException ex) {
            logger.error(EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException ex) {
            logger.error(UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException ex) {
            logger.error(JWT_CLAIMS_STRING_IS_EMPTY);
        }
        return false;
    }
}
