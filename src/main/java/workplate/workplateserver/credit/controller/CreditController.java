package workplate.workplateserver.credit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.common.ApiResponse;
import workplate.workplateserver.credit.domain.dto.CreditRequest;
import workplate.workplateserver.credit.domain.dto.CreditResponse;
import workplate.workplateserver.credit.service.CreditService;

/**
 * 크레딧 Controller
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CreditController {

	private final CreditService creditService;

	@GetMapping("/credits/{username}")
	public ApiResponse<CreditResponse> findByUsername(@PathVariable String username) {
		CreditResponse response = creditService.findCredit(username);

		return ApiResponse.success(response);
	}

	@PatchMapping("/credits/plus")
	public ApiResponse<CreditResponse> plusCredit(@RequestBody CreditRequest request) {
		CreditResponse response = creditService.plusCredit(request.getUsername(), request.getBalance());
		return ApiResponse.success(response);
	}

	@PatchMapping("/credits/minus")
	public ApiResponse<CreditResponse> minusCredit(@RequestBody CreditRequest request) {
		CreditResponse response = creditService.minusCredit(request.getUsername(), request.getBalance());
		return ApiResponse.success(response);
	}
}
