package tutorial.ecommerce_backend.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import tutorial.ecommerce_backend.api.DTO.LoginBody;
import tutorial.ecommerce_backend.config.JwtConfig;
import tutorial.ecommerce_backend.dao.LocalUserDao;
import tutorial.ecommerce_backend.entity.LocalUser;

@Service
public class JWTService {
	public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;
	@Autowired
	private JwtEncoder jwtEncoder;
	@Autowired
	private JwtConfig jwtConfig;
	@Autowired
	private LocalUserDao userDao;


	@Value("${jwt.access-token-validation-in-seconds}")
	private Long accessTokenExpiration;

	@Value("${jwt.refresh-token-validation-in-seconds}")
	private Long refreshTokenExpiration;

	public static final String AUTHORITY = "AUTHORITY";

	public Long getAccessTokenExpiration() {
		return accessTokenExpiration;
	}

	public void setAccessTokenExpiration(Long accessTokenExpiration) {
		this.accessTokenExpiration = accessTokenExpiration;
	}

	public Long getRefreshTokenExpiration() {
		return refreshTokenExpiration;
	}

	public void setRefreshTokenExpiration(Long refreshTokenExpiration) {
		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	public String createAccessToken(Authentication authentication) {
		Instant now = Instant.now();
		Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);

		// lấy ra quyền của người dùng
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		JwtClaimsSet claimsSet = JwtClaimsSet.builder().issuedAt(now).expiresAt(validity)
				.subject(authentication.getName()).claim(AUTHORITY, authorities).build();

		JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

		String token = this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
		System.out.println("Created Access Token: " + token);
		return token;
	}

	public String createRefreshToken(Authentication authentication, LoginBody loginBody) {
		Instant now = Instant.now();
		Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);

		// lấy ra quyền của người dùng
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		JwtClaimsSet claimsSet = JwtClaimsSet.builder().issuedAt(now).expiresAt(validity)
				.subject(authentication.getName()).claim(AUTHORITY, authorities).build();

		JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
		String token = this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
		System.out.println("Created Refresh Token: " + token);
		return token;
	}

	// lấy ra người dùng
	public LocalUser getUsernameByToken(String token) {
		Boolean isValid = this.isValidAccessToken(token);
		if (!isValid) {
			return null;
		}
		Jwt jwt = jwtConfig.jwtDecoder().decode(token);
		String userNameFromToken = jwt.getSubject();
		LocalUser currentUser = userDao.findByEmailOrUsername(userNameFromToken, userNameFromToken)
				.orElseThrow(() -> new UsernameNotFoundException("Not found user"));
		return currentUser;
	}
	
	
	// kiểm tra accesstokeb đã hết hạn chưa
		public boolean isValidAccessToken(String token) {
			try {
				Jwt jwt = jwtConfig.jwtDecoder().decode(token);

				// Check if the token is expired
				Instant expirationTime = jwt.getExpiresAt();
				if (expirationTime != null && expirationTime.isBefore(Instant.now())) {
					System.out.println("Acess token has expired.");
					return false;
				}

				// If the token is not expired
				System.out.println("access token is valid.");
				return true;
			} catch (JwtException e) {
				// Handle exceptions if the token is invalid
				System.out.println("Invalid access token: " + e.getMessage());
				return false;
			}

		}

	// kiểm tra refreshToken đã hết hạn chưa
	public boolean isValidRefreshToken(String token) {
		try {
			Jwt jwt = jwtConfig.jwtDecoder().decode(token);

			// Check if the token is expired
			Instant expirationTime = jwt.getExpiresAt();
			if (expirationTime != null && expirationTime.isBefore(Instant.now())) {
				System.out.println("Refresh token has expired.");
				return false;
			}

			// If the token is not expired
			System.out.println("Refresh token is valid.");
			return true;
		} catch (JwtException e) {
			// Handle exceptions if the token is invalid
			System.out.println("Invalid refresh token: " + e.getMessage());
			return false;
		}

	}


}
