package workplate.workplateserver.auth.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.auth.domain.entity.Member;

/**
 * 회원 정보를 담는 클래스
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final Member member;

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add((GrantedAuthority)() -> member.getUserRole().toString());
		return collection;
	}
}
