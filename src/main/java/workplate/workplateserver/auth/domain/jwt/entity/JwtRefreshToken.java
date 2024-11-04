package workplate.workplateserver.auth.domain.jwt.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : parkjihyeok
 * @since : 2024/11/04
 */
@Getter
@RedisHash(value = "JwtRefreshToken", timeToLive = 60 * 60 * 24 * 10) // 리프레시 토큰의 유효기간은 10일
@RequiredArgsConstructor
public class JwtRefreshToken {

	@Id
	private final String username;
	private final String refreshToken;
}
