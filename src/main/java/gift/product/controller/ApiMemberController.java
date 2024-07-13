package gift.product.controller;

import gift.product.dto.MemberDTO;
import gift.product.model.Member;
import gift.product.service.MemberService;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class ApiMemberController {

    private final MemberService memberService;

    @Autowired
    public ApiMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> signUp(
            @Valid @RequestBody MemberDTO memberDTO,
            BindingResult bindingResult
    ) {
        System.out.println("[ApiMemberController] signUp()");

        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }

        return memberService.signUp(memberDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @Valid @RequestBody MemberDTO memberDTO,
            BindingResult bindingResult
    ) {
        System.out.println("[ApiMemberController] login()");

        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }

        return memberService.login(memberDTO);
    }
}
