package workplate.workplateserver.auth.domain.jwt.repository;

import org.springframework.data.repository.CrudRepository;

import workplate.workplateserver.auth.domain.jwt.entity.JwtRefreshToken;

/**
 * @author : parkjihyeok
 * @since : 2024/11/04
 */
public interface JwtRefreshTokenRepository extends CrudRepository<JwtRefreshToken, String> {
}
