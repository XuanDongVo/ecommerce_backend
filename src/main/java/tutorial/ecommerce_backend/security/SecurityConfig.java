package tutorial.ecommerce_backend.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTRequestFilter filter;
       

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Vô hiệu hóa bảo vệ CSRF nếu sử dụng phiên không trạng thái với JWT
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Cấu hình CORS
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class) 
            .authorizeHttpRequests(authorize -> authorize
            		 .requestMatchers("/admin/**").hasRole("ADMIN") // Các route admin yêu cầu vai trò ADMIN
                .requestMatchers("/**").permitAll() // Cho phép tất cả các route khác
                .anyRequest().authenticated() // Đảm bảo các yêu cầu khác đã được xác thực
            )
            
            .formLogin(form -> form
                .disable() // Tắt form login nếu không sử dụng
            )
            
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // Xử lý lỗi xác thực cho API
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Không sử dụng session
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/auth/logout") // URL để thực hiện logout
                .permitAll() // Cho phép tất cả người dùng truy cập URL logout
            )
            .httpBasic(withDefaults()); // Bật xác thực HTTP cơ bản
        
        return http.build();
    }

    
//    @Bean
//    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }
    
    // chuyển hướng người dùng tới trang lúc yêu cầu 
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
                if (redirectUrl != null) {
                    request.getSession().removeAttribute("redirectUrl");
                    response.sendRedirect(redirectUrl);
                } else {
                    response.sendRedirect("/");
                }
            }
        };
    }



    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }	

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Sử dụng BCryptPasswordEncoder để mã hóa mật khẩu
    }
    

    @Bean
    public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {
        ConcurrentSessionControlAuthenticationStrategy strategy = 
            new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        strategy.setMaximumSessions(1);
        strategy.setExceptionIfMaximumExceeded(false);
        return strategy;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

