package tutorial.ecommerce_backend.security.session;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Kiem tra va huy phien hoat dong khac cua nguoi dung
 */

@Component
public class CustomSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

	private final SessionRegistry sessionRegistry;

	public CustomSessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	@Override
	public void onAuthentication(org.springframework.security.core.Authentication authentication,
			HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
		String username = authentication.getName();
		sessionRegistry.getAllPrincipals().forEach(principal -> {
			if (principal instanceof org.springframework.security.core.userdetails.User) {
				org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
				if (user.getUsername().equals(username)) {
					sessionRegistry.getAllSessions(principal, false).forEach(sessionInformation -> {
						sessionInformation.expireNow();
					});
				}
			}
		});
	}
}