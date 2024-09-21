package tutorial.ecommerce_backend.service;

import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import tutorial.ecommerce_backend.api.DTO.LoginBody;
import tutorial.ecommerce_backend.api.DTO.LoginResponse;
import tutorial.ecommerce_backend.api.DTO.RegistrationBody;
import tutorial.ecommerce_backend.dao.CartDao;
import tutorial.ecommerce_backend.dao.LocalUserDao;
import tutorial.ecommerce_backend.dao.RoleDao;
import tutorial.ecommerce_backend.entity.Cart;
import tutorial.ecommerce_backend.entity.LocalUser;
import tutorial.ecommerce_backend.exception.UserException;
import tutorial.ecommerce_backend.jwt.JWTService;

@Service
public class UserService {

	private LocalUserDao userDao;
	private JWTService jwtService;
	private RoleDao roleDao;
	private CartDao cartDao;
	private AuthenticationManagerBuilder authenticationManagerBuilder;
	private PasswordEncoder passwordEncoder;

	public UserService(LocalUserDao userDao, JWTService jwtService, RoleDao roleDao, CartDao cartDao,
			AuthenticationManagerBuilder authenticationManagerBuilder, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.jwtService = jwtService;
		this.roleDao = roleDao;
		this.cartDao = cartDao;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.passwordEncoder = passwordEncoder;
	}

	public void registerUser(RegistrationBody registrationBody) throws UserException {
		if (userDao.findByEmail(registrationBody.getEmail()).isPresent()
				|| userDao.findByUsername(registrationBody.getUsername()).isPresent()) {
			throw new UserException();
		}
		LocalUser user = new LocalUser();
		user.setUsername(registrationBody.getUsername());
		user.setEmail(registrationBody.getEmail());
		
		String hashPassword = passwordEncoder.encode(registrationBody.getPassword());
		user.setPassword(hashPassword);
		  user.setRole(roleDao.findByName("ROLE_USER"));
		userDao.save(user);

		Optional<LocalUser> opUser = userDao.findByUsername(registrationBody.getUsername());
		if (opUser.isPresent()) {
			Cart cart = new Cart();
			cart.setUser(opUser.get()); // Set the user in the cart
			cartDao.save(cart); // Save the cart
		}
	}

	public LoginResponse loginUser(LoginBody loginBody , HttpServletResponse response) {		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginBody.getEmailOrUserName(), loginBody.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// tao access token
		String accesstoken = jwtService.createAccessToken(authentication);
		
		   response.addHeader("Authorization", "Bearer " + accesstoken);
		   
		// Tra ve object LoginResponse
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setJwt(accesstoken);
		
		LoginResponse.UserResponse  userResponse = new LoginResponse.UserResponse();
		
		LocalUser currentUser = userDao.findByEmailOrUsername(loginBody.getEmailOrUserName(), loginBody.getEmailOrUserName())
				.orElseThrow(() -> new UsernameNotFoundException("Not found user by " + loginBody.getEmailOrUserName()));
		userResponse.setId(currentUser.getId());
		userResponse.setUsername(currentUser.getUsername());
		userResponse.setEmail(currentUser.getEmail());
		
		loginResponse.setUserResponse(userResponse);
		
		// tao refresh token
		String refresh_token = jwtService.createRefreshToken(authentication, loginBody);
		currentUser.setRefreshToken(refresh_token);
		userDao.save(currentUser);
	
//		// set cookies
		ResponseCookie cookie = ResponseCookie.from("refresh_token", refresh_token)
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(jwtService.getRefreshTokenExpiration())
				.build();
		  response.addHeader("Set-Cookie", cookie.toString());
		 
		
		return loginResponse ;
	}
}
