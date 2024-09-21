package tutorial.ecommerce_backend.api.controller.auth;

import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import tutorial.ecommerce_backend.api.DTO.LoginBody;
import tutorial.ecommerce_backend.api.DTO.LoginResponse;
import tutorial.ecommerce_backend.api.DTO.RegistrationBody;
import tutorial.ecommerce_backend.exception.UserException;
import tutorial.ecommerce_backend.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private UserService userService;


	@PostMapping("/register")
	public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
		try {
			userService.registerUser(registrationBody);

			return ResponseEntity.ok().build();
		} catch (UserException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody , HttpServletResponse response)  {
		
		try {
			 LoginResponse loginResponse =  userService.loginUser(loginBody ,response);
			 return ResponseEntity.ok(loginResponse);
		} catch (Exception e) {
			System.out.println("error " + e );
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}


	@GetMapping("/check")
	public ResponseEntity<?> getLoggedInUserProfile(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7); // Loại bỏ "Bearer " khỏi token
			return ResponseEntity.ok(token); // Trả về thông báo nếu token hợp lệ
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); // Trả về lỗi nếu không có token
	}

	@PostMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response , Authentication authentication) {
		// Xóa cookie chứa JWT
		ResponseCookie cookie = ResponseCookie.from("jwt", "").httpOnly(true).secure(true).path("/").maxAge(0) // Xóa
																												// cookie
				.build();
		response.addHeader("Set-Cookie", cookie.toString());
		// Xóa phiên làm việc
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, response, authentication);

		return ResponseEntity.ok().build();
	}

}
