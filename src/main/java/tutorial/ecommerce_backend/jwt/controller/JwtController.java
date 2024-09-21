package tutorial.ecommerce_backend.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tutorial.ecommerce_backend.entity.LocalUser;
import tutorial.ecommerce_backend.jwt.JWTService;

@RestController
public class JwtController {
	@Autowired
	private JWTService jwtService;

	@GetMapping("/jwt/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
	    String refreshToken = null;
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("refresh_token")) {
	                refreshToken = cookie.getValue();
	            }
	        }
	    }

	    try {
	        if (refreshToken != null) {
	            // Kiểm tra tính hợp lệ của refresh token
	            boolean isValid = jwtService.isValidRefreshToken(refreshToken);
	            if (isValid) {
	                // Tạo access token mới từ refresh token
	                LocalUser user = jwtService.getUsernameByToken(refreshToken);

	                String jwtDatabase = user.getRefreshToken();
	                
	                if (refreshToken.equals(jwtDatabase)) {
	                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	                    String newAccessToken = jwtService.createAccessToken(authentication);

	                    // Gửi access token mới về cho frontend
	                    response.setHeader("Authorization", "Bearer " + newAccessToken);
	                } else {
	                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                    response.getWriter().write("Unauthorized: Invalid refresh token");
	                }
	            } else {
	                // Thông báo khi refresh token đã hết hạn
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                response.getWriter().write("Unauthorized: Refresh token has expired");
	            }
	        } else {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getWriter().write("Bad Request: Refresh token is missing");
	        }
	    } catch (Exception e) {
	        System.out.println(e);
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        try {
	            response.getWriter().write("Internal Server Error: " + e.getMessage());
	        } catch (Exception ex) {
	            System.out.println(ex);
	        }
	    }
	}

}
