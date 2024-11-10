package workplate.workplateserver.auth.domain.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import workplate.workplateserver.auth.domain.jwt.JwtTokenProvider;
import workplate.workplateserver.auth.domain.jwt.entity.JwtAccessToken;
import workplate.workplateserver.auth.domain.jwt.entity.JwtRefreshToken;
import workplate.workplateserver.auth.domain.jwt.repository.JwtAccessTokenRepository;
import workplate.workplateserver.auth.domain.jwt.repository.JwtRefreshTokenRepository;
import workplate.workplateserver.common.ApiResponse;

/**
 * 로그인 성공 핸들러
 *
 * @author : parkjihyeok
 * @since : 2024/11/04
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtAccessTokenRepository jwtAccessTokenRepository;
	private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final int accessTokenValidTime = 10 * 60;    // 액세스 토큰의 유효기간은 10분
	private final int refreshTokenValidTime = 10 * 24 * 60 * 60;    // 리프레시 토큰의 유효기간은 10일

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		String username = authentication.getName();
		String accessToken = jwtTokenProvider.createAccessToken(username);
		String refreshToken = jwtTokenProvider.createRefreshToken(username);

		// 로그인한 id 를 키 값으로 생성한 액세스토큰을 redis 에 저장
		jwtAccessTokenRepository.save(new JwtAccessToken(username, accessToken));

		// 로그인한 id 를 키 값으로 생성된 리프레시토큰 을 redis 에 저장
		jwtRefreshTokenRepository.save(new JwtRefreshToken(username, refreshToken));

		// 생성된 객체를 클라이언트에게 전달
		response.addCookie(createCookie("username", username, refreshTokenValidTime));
		response.addCookie(createCookie("Authorization", accessToken, accessTokenValidTime));
		response.addCookie(createCookie("RefreshToken", refreshToken, refreshTokenValidTime));
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ApiResponse<String> apiResponse = ApiResponse.success("로그인에 성공하였습니다.", null);
		response.getWriter().println(new Gson().toJson(apiResponse));
	}

	private Cookie createCookie(String key, String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		// cookie.setSecure(true); HTTPS 에서만 사용
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		return cookie;
	}
}
