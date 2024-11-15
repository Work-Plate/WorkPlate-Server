package workplate.workplateserver.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 직종 소분류
 *
 * @author : parkjihyeok
 * @since : 2024/11/15
 */
@Getter
@RequiredArgsConstructor
public enum SubExperience {
	// Cooking and Serving
	CHEF_COOK("주방장·조리사"),
	KITCHEN_STAFF("주방·주방보조·설거지"),
	SERVING_PACKAGING("서빙·포장"),
	COUNTER("카운터"),
	STORE_MANAGER("점장·매니저"),
	OTHER_COOKING_SERVING("요리·서빙 기타"),

	// Production and Technical
	TEXTILE("섬유·재단·미싱"),
	PRODUCTION_MANUFACTURING("생산·제조·조립·인쇄"),
	PACKAGING_INSPECTION("포장·검사"),
	INSTALLATION_REPAIR("설치·수리·정비·AS"),
	FARM_AGRICULTURE("농장·농사"),
	METALWORK_MOLD("금속·금형"),
	AUTO_REPAIR_SHIPBUILDING("자동차정비·조선·선원"),
	MACHINERY_EQUIPMENT("기계·설비"),
	FOOD_MANUFACTURING("농수산물 가공·식품 제조"),
	OTHER_PRODUCTION_TECHNICAL("생산·기술 기타"),

	// Driving and Delivery
	PARCEL_DELIVERY("택배·물류"),
	CARGO_SPECIAL_VEHICLES("화물·중장비·특수차"),
	INDEPENDENT_CONTRACTOR("지입·차량용역"),
	BUS_TAXI_VAN("버스·택시·승합차"),
	DELIVERY_MOVING("배송·이사"),
	QUICK_SERVICE("배달·퀵·이륜차"),
	CHAUFFEUR("승용차·대리운전"),
	OTHER_DRIVING_DELIVERY("운전·배달 기타"),

	// Construction
	CONSTRUCTION_FINISHING("건설마감"),
	CONSTRUCTION_STRUCTURE("건설구조"),
	WELDING_CUTTING("용접·절단"),
	PLUMBING_FACILITIES("배관·설비"),
	ELECTRICAL_MANAGEMENT("전기·조명·시설관리"),
	CONSTRUCTION_SITE("건설현장"),
	OTHER_CONSTRUCTION("건설·토목 기타"),

	// Sales and Distribution
	MART_SUPERMARKET("마트·슈퍼"),
	CONVENIENCE_STORE("편의점"),
	LIVESTOCK_PRODUCE("축산·청과·농수산"),
	DEPARTMENT_STORE("백화점·아울렛·쇼핑몰"),
	LARGE_RETAIL_STORE("대형마트·유통점"),
	CLOTHING_ACCESSORIES("의류·주얼리·잡화"),
	FLORIST("꽃·화훼"),
	COSMETICS_BEAUTY("화장품·뷰티·헬스스토어"),
	OTHER_SALES_DISTRIBUTION("유통·판매 기타"),

	// Culture and Leisure
	HOTEL_LODGING("모텔·호텔·숙박"),
	SAUNA_SPA("사우나·찜질방"),
	HIGHWAY_REST_STOP("고속도로 휴게소"),
	SPORTS("당구장·볼링장·스포츠"),
	GOLF("골프·스크린골프"),
	ARCADE_PC_ROOM("오락실·PC방"),
	BEAUTY("헤어·네일·피부·미용"),
	PET_SHOP("반려동물·애견샵"),
	STUDY_CAFE("독서실·고시원·스터디카페"),
	OTHER_LIFESTYLE("문화·여가·생활 기타"),

	// Services
	CLEANING_SANITATION("청소·미화·방역"),
	SECURITY_GUARD("경비·보안"),
	CAR_WASH_REFUELING("세차·주유"),
	PARKING_MANAGEMENT("주차관리·주차도우미"),
	LAUNDRY_ALTERATION("세탁·수선"),
	HOUSEKEEPER_BABYSITTER("가사도우미·베이비시터"),
	FUNERAL_WEDDING("상조·웨딩·연회"),
	OTHER_SERVICE("서비스 기타"),

	// Office Work
	ACCOUNTING_FINANCE("경리·세무·회계"),
	OFFICE_PLANNING("사무·기획·관리·홍보"),
	GENERAL_AFFAIRS("총무·노무·법무·인사"),
	IT_DESIGN("컴퓨터·IT·디자인"),
	RECEPTION_SECRETARY("안내·비서"),
	OTHER_OFFICE_WORK("사무·회계·IT 기타"),

	// Sales and Consulting
	INBOUND_CS("인바운드·CS"),
	OUTBOUND_TM("아웃바운드·TM"),
	REAL_ESTATE("부동산"),
	GENERAL_SALES("일반·기술영업"),
	FINANCE_INSURANCE("금융·보험·카드"),
	OTHER_SALES_CONSULTING("상담·영업기타"),

	// Medical and Care
	ELDERLY_CARE("요양·간병·돌봄"),
	NURSING_ASSISTANT("간호조무사·간호사"),
	MEDICAL_TECHNICIAN("의료기사·치료사"),
	DOCTOR("의사·한의사"),
	OTHER_MEDICAL_CARE("요양·간호·의료 기타"),

	// Education and Instruction
	DAYCARE("어린이집·유치원"),
	ENTRANCE_EXAM("입시·보습·자격증"),
	PRIVATE_TUTORING("과외·공부방·학습지"),
	SPORTS_INSTRUCTION("생활체육·스포츠"),
	MUSIC_ART("음악·피아노·미술"),
	OTHER_EDUCATION("교육·강사 기타");

	private final String subExperience;
}
