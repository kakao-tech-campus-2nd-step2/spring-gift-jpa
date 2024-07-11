package gift.member;

import gift.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    public MemberController(MemberService memberService, JwtService jwtService){
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("newSiteUser", new Member());
        return "signup";
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> createUser(@RequestBody Member newMember){
        memberService.createMember(newMember.getUsername(), newMember.getEmail(), newMember.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(""));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest user){
        Member member = memberService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        String jwt = jwtService.createJwt(member.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(jwt));
    }

}
