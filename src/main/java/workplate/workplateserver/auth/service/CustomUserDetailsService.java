package workplate.workplateserver.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.auth.domain.CustomUserDetails;
import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.common.CommonService;

/**
 * 로그인 로직
 *
 * @author : parkjihyeok
 * @since : 2024/11/04
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final CommonService commonService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = commonService.findByUsername(username, false);
		return new CustomUserDetails(member);
	}
}
