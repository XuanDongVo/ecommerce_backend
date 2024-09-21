package tutorial.ecommerce_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import tutorial.ecommerce_backend.config.CorsConfig;
import tutorial.ecommerce_backend.config.JwtConverterConfig;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	 @Autowired
	    private JwtConverterConfig converterConfig;
	 @Autowired 
	 private CorsConfig corsConfig;
       

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource())) // Cấu hình CORS
            .authorizeHttpRequests(authorize -> authorize
            		 .requestMatchers("/admin/**").hasRole("ADMIN") // Các route admin yêu cầu vai trò ADMIN
                .requestMatchers("/**" ).permitAll() // Cho phép tất cả các route khác
                .anyRequest().authenticated() // Đảm bảo các yêu cầu khác đã được xác thực
            )
            
				.oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(converterConfig.jwtAuthenticationConverter()))
                    .jwt(Customizer.withDefaults())
					)
    				.exceptionHandling(
    						exception -> exception.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()) //401
    												.accessDeniedHandler(new BearerTokenAccessDeniedHandler()) //403
    						)
            
            .formLogin(form -> form
                .disable()
            )
            
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Không sử dụng session
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/auth/logout") // URL để thực hiện logout
                .permitAll() // Cho phép tất cả người dùng truy cập URL logout
            );
        
        return http.build();
    }
    
    
    
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    
   
   
}

