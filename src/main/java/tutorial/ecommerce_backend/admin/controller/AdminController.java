package tutorial.ecommerce_backend.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import tutorial.ecommerce_backend.admin.service.AdminService;
import tutorial.ecommerce_backend.api.DTO.LoginBody;
import tutorial.ecommerce_backend.api.DTO.LoginResponse;
import tutorial.ecommerce_backend.api.DTO.RegistrationBody;
import tutorial.ecommerce_backend.exception.UserException;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@PostMapping("/register")
	public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
		try {
			adminService.registerUser(registrationBody);

			return ResponseEntity.ok().build();
		} catch (UserException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @GetMapping("/dashboard")
	    public ResponseEntity<String> getDashboard() {
	        return ResponseEntity.ok("Admin Dashboard");
	    }

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody, HttpServletResponse response) {
	    String jwt = adminService.loginUser(loginBody);
	    System.out.println(jwt);
	    if (jwt == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }

	    // Lưu JWT vào cookie
	    ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
//	            .httpOnly(true)
	            .secure(false) // Để thử nghiệm, đặt false để không yêu cầu HTTPS
	            .path("/")
	            .maxAge(60 * 60) // 1 giờ
	            .build();
	    response.addHeader("Set-Cookie", cookie.toString());

	    System.out.println("Set-Cookie header: " + cookie.toString()); // Log header để kiểm tra

	    LoginResponse loginResponse = new LoginResponse();
	    loginResponse.setJwt(jwt);
	    return ResponseEntity.ok(loginResponse);
	}
	 
	 

}
