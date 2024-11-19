package workplate.workplateserver.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 컨트롤러단 예외처리기
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

	private void addCorsHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> common(Exception e, HttpServletResponse response) {
		addCorsHeaders(response); // CORS 헤더 추가
		log.error("[밥상일터]: 예상치 못한 예외가 발생하였습니다. 예외내용 = {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponse.failure("관리자에게 문의해주세요."));
	}

	@ExceptionHandler({IllegalArgumentException.class, AccessDeniedException.class})
	public ResponseEntity<ApiResponse<String>> illegalArgument(Exception e, HttpServletResponse response) {
		addCorsHeaders(response); // CORS 헤더 추가
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponse.failure(e.getMessage()));
	}

}
