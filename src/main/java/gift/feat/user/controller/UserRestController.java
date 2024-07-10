package gift.feat.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import gift.feat.user.dto.LoginRequestDto;
import gift.feat.user.dto.SignupRequestDto;
import gift.feat.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserRestController {
	private final UserService userService;

	// email과 패스워드를 입력하면, 해당 유저의 정보를 JWT access token으로 반환
	@PostMapping("/login")
	public String login(@RequestBody LoginRequestDto loginRequestDto) {
		return userService.checkEmailAndPassword(loginRequestDto.email(), loginRequestDto.password());
	}

	@PostMapping("/signup")
	public void signup(@RequestBody SignupRequestDto signupRequestDto) {
		userService.registerUser(signupRequestDto.toEntity());
	}
}
