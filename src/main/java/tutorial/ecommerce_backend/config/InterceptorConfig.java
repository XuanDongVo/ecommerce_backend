package tutorial.ecommerce_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Bean
	public InterceptorHandle getInterceptorConfig() {
		return new InterceptorHandle();
	}

	
	  @Override
      public void addInterceptors(InterceptorRegistry registry) {
		  registry.addInterceptor( getInterceptorConfig()).addPathPatterns("/**");
      }
}
