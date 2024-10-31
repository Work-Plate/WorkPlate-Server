package workplate.workplateserver.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.auth.domain.dto.request.JoinRequest;
import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.auth.repository.MemberRepository;

/**
 * 회원 서비스
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * 회원가입 로직
	 *
	 * @param joinRequest 회원가입 요청
	 */
	public void saveMember(JoinRequest joinRequest) {
		if (memberRepository.existsByUsername(joinRequest.getUsername())) {
			throw new IllegalArgumentException("이미 존재하는 회원아이디입니다.");
		}

		memberRepository.save(Member.toEntity(joinRequest.getUsername(), joinRequest.getName(),
				bCryptPasswordEncoder.encode(joinRequest.getPassword())));
	}
}
