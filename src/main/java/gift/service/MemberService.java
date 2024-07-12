package gift.service;

import gift.model.Member;
import gift.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final String secretKey;

    public MemberService(MemberRepository memberRepository,
        @Value("${secret.key}") String secretKey) {
        this.memberRepository = memberRepository;
        this.secretKey = secretKey;
    }

    public Optional<String> registerMember(Member member) {
        String hashedPassword = BCrypt.hashpw(member.getPassword(), BCrypt.gensalt());
        // BCrypt 알고리즘에서 salt를 생성 -> 해시 함수에 추가되는 랜덤한 값으로 같은 비밀번호라도 솔트가 다르면 다른 해시값을 생성
        // hash password -> 비밀번호와 솔트를 조합하여 BCrypt 해시 함수를 적용, 해시된 비밀번호를 문자열로 반환
        member.setPassword(hashedPassword); // 암호화된 비밀번호를 멤버의 password로 설정!
        Member savedMember = memberRepository.save(member); // 해당 멤버를 memberRepository에 저장

        // 클레임은 토큰에 대한 추가적인 정보를 제공하거나 토큰의 권한, 만료 시간 등을 명시하는 데 사용됩니다

        // Jwt = JSON Web Token
        String token = Jwts.builder() // Jwt Builder 객체 생성 -> Builder Pattern -> 김태연 교수님
            .subject(savedMember.getId().toString()) // Jwt의 subject는 saveMember의 ID로 한다
            .claim("email",
                savedMember.getEmail()) // Claim은 토큰에 포함된 정보의 한 조각으로 키-값 쌍으로 이루어짐. 여기서 "email"이라는 키에 savedMember의 이메일 값을 할당
            .signWith(Keys.hmacShaKeyFor(
                secretKey.getBytes())) // Jwt에 서명 추가. 서명은 토큰의 무결성을 보장, 토큰이 변조되지 않았음을 확인. 여기서는 HMAC-SHA 알고리즘을 사용하여 서명 생성. secretKey는 바이트 배열로 변환하여 전달된다.
            .compact(); // Jwt 빌더를 완성하고 압축된 형태의 Jwt 문자열을 생성, 이 문자열이 최종적으로 token 변수에 할당된다.

        return Optional.of(token); // Optional<String>으로 리턴
    }

    public Optional<String> login(String email, String password) {
        Optional<Member> memberOptional = memberRepository.findByEmail(
            email); // Email을 통해 repository에서 멤버 가져옴
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (BCrypt.checkpw(password, member.getPassword())) { // BCrypt를 통해 password를 확인한다
                String jws = Jwts.builder()
                    .subject(member.getId().toString())
                    .claim("email", member.getEmail())
                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .compact();
                return Optional.of(jws);
            }
        }
        return Optional.empty();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
}
