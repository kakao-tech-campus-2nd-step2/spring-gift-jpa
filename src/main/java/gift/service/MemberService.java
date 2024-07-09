package gift.service;

import gift.domain.Member;
import gift.domain.MemberRequest;
import gift.domain.MemberResponse;
import gift.repository.MemberRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private JwtService jwtService;

    public MemberService(MemberRepository memberRepository,JwtService jwtService){
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
    }

    public ResponseEntity<String> join(MemberRequest memberRequest) {
        memberRepository.save(new Member(memberRequest.id(),memberRequest.password()));
        String jwt = jwtService.createJWT(memberRequest.id());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",jwt);

        return ResponseEntity.ok().headers(headers).body("success");
    }

    public ResponseEntity<String> login(MemberRequest memberRequest) {
        Member dbMember = memberRepository.findById(memberRequest.id())
                .orElseThrow(() -> new NoSuchElementException("회원정보가 없습니다."));
        if(!memberRequest.password().equals(dbMember.getPassword())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("incorrect password or id");
        }
        else{
            String jwt = jwtService.createJWT(memberRequest.id());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization","basic " + jwt);
            return ResponseEntity.ok().headers(headers).body("success");
        }
    }
}
