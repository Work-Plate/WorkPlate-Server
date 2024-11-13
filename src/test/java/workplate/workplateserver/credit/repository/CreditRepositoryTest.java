package workplate.workplateserver.credit.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;
import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.auth.repository.MemberRepository;
import workplate.workplateserver.credit.domain.entity.Credit;

/**
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@DataJpaTest
@DisplayName("크레딧 Repository 테스트")
class CreditRepositoryTest {

	@Autowired
	EntityManager em;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	CreditRepository creditRepository;

	@Test
	void 회원정보를넘기면_크레딧을가져온다() {
	    // Given
		Member member = Member.toEntity("testId", "회원A", "pw");
		memberRepository.save(member);
		Credit credit = Credit.toEntity(member);
		creditRepository.save(credit);
		credit.plusBalance(1000L);

		em.flush();
		em.clear();

	    // When
		Credit result = creditRepository.findByMember(member).get();

		// Then
		assertEquals(1000, result.getBalance());
		assertEquals("회원A", result.getMember().getName());
	}
}
