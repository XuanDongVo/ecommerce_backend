package tutorial.ecommerce_backend.security.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import tutorial.ecommerce_backend.model.LocalUser;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    
    @Value("${jwt.issuer}")
    private String issuer;
    
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    private Algorithm algoAlgorithm;

    private static final String USERNAME_KEY = "USERNAME";

    @PostConstruct
    public void postConstruct() {
        algoAlgorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateJWT(LocalUser user) {
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getUsername())
                .withClaim("roles", user.getRole().getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algoAlgorithm);
    }

    public String getUserName(String token) {
        DecodedJWT jwt = JWT.require(algoAlgorithm).withIssuer(issuer).build().verify(token);
        return jwt.getClaim(USERNAME_KEY).asString();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        DecodedJWT jwt = JWT.require(algoAlgorithm).withIssuer(issuer).build().verify(token);
        return jwt.getExpiresAt().before(new Date());
    }
}
