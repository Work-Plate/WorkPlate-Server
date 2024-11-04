package workplate.workplateserver.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.auth.domain.filter.LoginFilter;
import workplate.workplateserver.auth.domain.handler.LoginSuccessHandler;
import workplate.workplateserver.auth.domain.jwt.filter.JwtFilter;

/**
 * 시큐리티 설정파일
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final LoginSuccessHandler loginSuccessHandler;
	private final JwtFilter jwtFilter;

	@Bean
	public LoginFilter loginFilter() throws Exception {
		LoginFilter loginFilter = new LoginFilter("/api/login");
		loginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
		loginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
		return loginFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/api/join", "/api/login").permitAll()
						// h2 허용
						.requestMatchers(PathRequest.toH2Console()).permitAll()
						.anyRequest().authenticated()
				)
				// h2 허용
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		http
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class) // 로그인 필터 변경
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // JWT 확인 필터
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
