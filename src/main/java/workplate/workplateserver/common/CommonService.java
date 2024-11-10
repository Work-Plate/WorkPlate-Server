package workplate.workplateserver.common;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.auth.repository.MemberRepository;

/**
 * 공통으로 사용되는 서비스 로직
 *
 * @author : parkjihyeok
 * @since : 2024/11/03
 */
@Service
@RequiredArgsConstructor
public class CommonService {

	private final MemberRepository memberRepository;

	/**
	 * 회원의 객체를 권한체크하여 반환하는 메서드
	 *
	 * @param username 회원 ID
	 * @param authCheck 권한 체크 여부
	 * @return 검색한 회원 객체
	 */
	public Member findByUsername(String username, boolean authCheck) {
		// 회원 엔티티만 요청
		if (!authCheck) {
			return findMember(username);
		}

		// 회원 엔티티와 권한 확인 요청
		Member member = findMember(username);
		if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(member.getUsername())) {
			throw new AccessDeniedException("회원에 대한 접근 권한이 없습니다.");
		}
		return member;
	}

	/**
	 * 회원 객체를 찾는 메서드
	 *
	 * @param username 회원 ID
	 * @return 회원 객체
	 */
	private Member findMember(String username) {
		return memberRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("회원정보를 찾을 수 없습니다."));
	}
}
