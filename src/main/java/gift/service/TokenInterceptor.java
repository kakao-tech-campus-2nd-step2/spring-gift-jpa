package gift.service;

import gift.model.BearerToken;
import gift.model.Member;
import gift.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import java.util.NoSuchElementException;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final MemberRepository memberRepository;
    static String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    public TokenInterceptor(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    @Value("${secret_key}")
    //    private String secretKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{String authHeader = request.getHeader("Authorization");
        BearerToken token = new BearerToken(authHeader);
        request.setAttribute("bearerToken", token);

        Claims claims = getClaims(token);
        request.setAttribute("claims", claims);

        Integer idInt = (Integer) claims.get("id");
        Long memberId = idInt.longValue();
        request.setAttribute("memberId", memberId);

        Member member = getMemberByAuth(memberId);
        request.setAttribute("member", member);

        return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    public Claims getClaims(BearerToken token) throws AuthenticationException {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.getToken())
                .getBody();
        return claims;
    }

    public Member getMemberByAuth(Long memberId) throws AuthenticationException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 멤버가 없습니다."));
    }
}
