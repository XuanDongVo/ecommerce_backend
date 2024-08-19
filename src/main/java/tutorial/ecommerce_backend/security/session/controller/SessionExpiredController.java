package tutorial.ecommerce_backend.security.session.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session-expired")
public class SessionExpiredController {

	@GetMapping
	public ResponseEntity<String> sessionExpired() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body("Your session has expired. You have been logged out due to a new login.");
	}
}