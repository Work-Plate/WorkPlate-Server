package workplate.workplateserver.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 직종 대분류
 *
 * @author : parkjihyeok
 * @since : 2024/11/03
 */
@Getter
@RequiredArgsConstructor
public enum MainExperience {
	COOKING_SERVING("요리·서빙"),
	PRODUCTION_TECHNICAL("생산·기술"),
	DRIVING_DELIVERY_LOGISTICS("운전·배달·물류"),
	CONSTRUCTION_CIVIL_ENGINEERING_LABOR("건설·토목·노무"),
	DISTRIBUTION_SALES("유통·판매"),
	CULTURE_LEISURE_LIFESTYLE("문화·여가·생활"),
	SERVICE("서비스"),
	OFFICE_ACCOUNTING_IT("사무·회계·IT"),
	SALES_CONSULTING("영업·상담"),
	CAREGIVING_NURSING_MEDICAL("요양·간호·의료"),
	EDUCATION_INSTRUCTION("교육·강사");

	private final String mainExperience;
}
