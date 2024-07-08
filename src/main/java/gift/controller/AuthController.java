package gift.controller;

import gift.dto.request.MemberRequestDto;
import gift.dto.response.MemberResponseDto;
import gift.service.AuthService;
import gift.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @ResponseBody
    @PostMapping("/members/register")
    public ResponseEntity<Map<String, String>> memberSignUp(@RequestBody MemberRequestDto memberRequestDto){
        authService.memberJoin(memberRequestDto);

        Map<String, String> response = getToken(memberRequestDto.email());

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }


    @ResponseBody
    @PostMapping("/members/login")
    public ResponseEntity<Map<String, String>> memberLogin(@RequestBody MemberRequestDto memberRequestDto){

        MemberResponseDto memberResponseDto = authService.findOneByEmailAndPassword(memberRequestDto);

        Map<String, String> response = getToken(memberResponseDto.email());

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    public Map<String, String> getToken(String memberRequestDto) {
        UUID uuid = UUID.randomUUID();
        tokenService.tokenSave(uuid.toString(), memberRequestDto);

        Map<String, String> response = new HashMap<>();
        response.put("token", uuid.toString());
        return response;
    }

}
