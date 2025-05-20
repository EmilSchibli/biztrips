package ch.clip.trips;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SpringWebConfig implements WebMvcConfigurer {
	/**
	 * CORS - Policy - from known Servers
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/products/*").allowedOrigins("http://localhost:3001");
		// configure CORS for used endpoints!!!!
		registry.addMapping("/trips/*").allowedOrigins("*");
	 	registry.addMapping("/meeting/*").allowedOrigins("*");
		registry.addMapping("/meeting/items/").allowedOrigins("*");
	}
}
