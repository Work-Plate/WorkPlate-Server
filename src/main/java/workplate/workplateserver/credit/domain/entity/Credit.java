package workplate.workplateserver.credit.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.common.BaseEntity;

/**
 * 결제 크레딧 Entity
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Credit extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	private Long balance;

	public void plusBalance(Long balance) {
		this.balance += balance;
	}

	public void minusBalance(Long balance) {
		if (this.balance - balance < 0) {
			throw new IllegalArgumentException("잔액은 0원 이하가 될 수 없습니다.");
		}
		this.balance -= balance;
	}

	public static Credit toEntity(Member member) {
		return Credit.builder()
				.member(member)
				.balance(0L)
				.build();
	}
}
