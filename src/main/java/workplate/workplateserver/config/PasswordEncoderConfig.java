package workplate.workplateserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 패스워드 인코더
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@Configuration
public class PasswordEncoderConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
