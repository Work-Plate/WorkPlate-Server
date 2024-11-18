package workplate.workplateserver.auth.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import workplate.workplateserver.auth.domain.MainExperience;
import workplate.workplateserver.auth.domain.PhysicalStatus;
import workplate.workplateserver.auth.domain.SubExperience;
import workplate.workplateserver.auth.domain.dto.request.MemberDetailRequest;
import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.auth.domain.entity.MemberDetail;

/**
 * @author : parkjihyeok
 * @since : 2024/11/03
 */
@DataJpaTest
@Transactional
@DisplayName("회원 상세정보 Repository 테스트")
class MemberDetailRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	MemberDetailRepository memberDetailRepository;

	Member member = Member.toEntity("testId", "testName", "testPW");

	@Test
	@DisplayName("회원 상세정보 저장 테스트")
	void saveTest() {
		// Given
		MemberDetailRequest request = new MemberDetailRequest("testId", 70, "서울", MainExperience.OFFICE_ACCOUNTING_IT,
				SubExperience.ACCOUNTING_FINANCE, MainExperience.OFFICE_ACCOUNTING_IT, SubExperience.ACCOUNTING_FINANCE,
				PhysicalStatus.NORMAL);
		MemberDetail md = MemberDetail.toEntity(member, request);
		memberDetailRepository.save(md);

		// When
		MemberDetail result = memberDetailRepository.findById(md.getId()).get();

		// Then
		assertAll(
				() -> assertEquals(md.getAge(), result.getAge()),
				() -> assertEquals(md.getMainExperience(), result.getMainExperience()),
				() -> assertEquals(md.getSubExperience(), result.getSubExperience()),
				() -> assertEquals(md.getPhysicalStatus(), result.getPhysicalStatus())
		);
	}

	@Test
	@DisplayName("회원 상세정보 삭제 테스트")
	void deleteTest() {
		// Given
		MemberDetailRequest request = new MemberDetailRequest("testId", 70,  "서울", MainExperience.OFFICE_ACCOUNTING_IT,
				SubExperience.ACCOUNTING_FINANCE, MainExperience.OFFICE_ACCOUNTING_IT, SubExperience.ACCOUNTING_FINANCE,
				PhysicalStatus.NORMAL);
		MemberDetail md = MemberDetail.toEntity(member, request);
		memberDetailRepository.save(md);

		// When
		memberDetailRepository.deleteById(md.getId());

		// Then
		assertEquals(Optional.empty(), memberDetailRepository.findById(md.getId()));
		;
	}
}
