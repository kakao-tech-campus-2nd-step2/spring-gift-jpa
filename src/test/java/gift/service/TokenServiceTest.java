package gift.service;

import gift.domain.Member;
import gift.domain.TokenAuth;
import gift.exception.UnAuthorizationException;
import gift.repository.member.MemberSpringDataJpaRepository;
import gift.repository.token.TokenSpringDataJpaRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @Mock
    private TokenSpringDataJpaRepository tokenRepository;

    @Mock
    private MemberSpringDataJpaRepository memberRepository;

    @InjectMocks
    private TokenService tokenService;

    private final static Long MEMBER_ID = 1L;
    private final static String EMAIL = "test@example.com";
    private final static String TOKEN = "test_token";

    @BeforeEach
    public void setup() {
        Member member = new Member(EMAIL, "password");
        member.setId(MEMBER_ID);

        TokenAuth tokenAuth = new TokenAuth(TOKEN, member);
        when(tokenRepository.save(any(TokenAuth.class))).thenReturn(tokenAuth);
        when(tokenRepository.findByToken(TOKEN)).thenReturn(Optional.of(tokenAuth));
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
    }

    @Test
    public void testSaveToken() {
        Member member = new Member(EMAIL, "password");
        member.setId(MEMBER_ID);

        String token = tokenService.saveToken(member);

        assertNotNull(token);
        assertTrue(token.startsWith("Bearer "));
    }

    @Test
    public void testFindToken() {
        TokenAuth tokenAuth = tokenService.findToken(TOKEN);

        assertNotNull(tokenAuth);
        assertEquals(EMAIL, tokenAuth.getMember().getEmail());
    }

    @Test
    public void testGetMemberIdFromToken() {
        String token = "Bearer " + createToken(MEMBER_ID.toString(), EMAIL);

        String memberId = tokenService.getMemberIdFromToken(token);

        assertEquals(MEMBER_ID.toString(), memberId);
    }

    @Test
    public void testGetMemberIdFromInvalidToken() {
        assertThrows(UnAuthorizationException.class,
                () -> tokenService.getMemberIdFromToken("invalid_token"));
    }

    private String createToken(String memberId, String email) {
        String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
        return Jwts.builder()
                .setSubject(memberId)
                .claim("email", email)
                .signWith(getSecretKey(secretKey))
                .compact();
    }


    private SecretKey getSecretKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
