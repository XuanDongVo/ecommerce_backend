package tutorial.ecommerce_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tutorial.ecommerce_backend.jwt.JWTService;

public class InterceptorHandle implements  HandlerInterceptor {
	@Autowired
	private JWTService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String authorizationHeader = request.getHeader("Authorization");
		// nguoi dung chua dang nhap chua co token
		if(authorizationHeader == null)
		{
			return true;
		}
		
		  String token = authorizationHeader.substring(7); // Loại bỏ "Bearer " khỏi token
		 boolean isValid =  jwtService.isValidAccessToken(token);
		 if(!isValid) {
			 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			 response.getWriter().write("{\"error\": \"Unauthorized: Token is missing or malformed\"}");
			 return false;
		 }
		 
		return true;
	}
}