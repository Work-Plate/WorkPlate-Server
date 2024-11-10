package workplate.workplateserver.auth.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.auth.domain.dto.request.JoinRequest;
import workplate.workplateserver.auth.domain.dto.request.MemberDetailRequest;
import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.auth.domain.entity.MemberDetail;
import workplate.workplateserver.auth.repository.MemberDetailRepository;
import workplate.workplateserver.auth.repository.MemberRepository;
import workplate.workplateserver.common.CommonService;

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
	private final MemberDetailRepository memberDetailRepository;
	private final CommonService commonService;
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

	/**
	 * 회원 상세정보 등록 메서드
	 *
	 * @param request 등록 요청
	 */
	public void saveDetails(MemberDetailRequest request) {
		Member member = commonService.findByUsername(request.getUsername(), true);

		MemberDetail memberDetail = MemberDetail.toEntity(member, request.getAge(), request.getExperience(),
				request.getPhysicalStatus(),
				request.getPreference());

		memberDetailRepository.save(memberDetail);
	}
}
