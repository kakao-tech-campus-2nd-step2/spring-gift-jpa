package gift.controller;
import gift.domain.JwtToken;
import gift.entity.Member;
import gift.dto.JwtResponseDto;
import gift.dto.UserRequestDto;
import gift.exception.LoginException;
import gift.service.MemberService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final JwtToken jwtToken;

    public MemberController(MemberService memberService, JwtToken jwtToken) {
        this.memberService = memberService;
        this.jwtToken = jwtToken;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        }
        Long userId = memberService.register(new Member(userRequestDto.getEmail(),
            userRequestDto.getPassword()));
        if(userId == -1L){
            throw new LoginException("이메일이 이미 존재합니다.");
        }
        String token = jwtToken.createToken(new Member(userRequestDto.getEmail(), userRequestDto.getPassword()));
        return new ResponseEntity<>(new JwtResponseDto(token),HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

        }
        Optional<Member> user = memberService.login(userRequestDto.getEmail(), userRequestDto.getPassword());
        if(user.isPresent()){
            String token = jwtToken.createToken(user.get());
            return new ResponseEntity<>(new JwtResponseDto(token),HttpStatus.OK);
        }
        throw new LoginException("이메일이나 비밀번호가 틀렸습니다.");
    }
}