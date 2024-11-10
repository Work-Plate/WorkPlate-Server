package workplate.workplateserver.auth.domain.jwt.repository;

import org.springframework.data.repository.CrudRepository;

import workplate.workplateserver.auth.domain.jwt.entity.JwtAccessToken;

/**
 * @author : parkjihyeok
 * @since : 2024/11/04
 */
public interface JwtAccessTokenRepository extends CrudRepository<JwtAccessToken, String> {
}
