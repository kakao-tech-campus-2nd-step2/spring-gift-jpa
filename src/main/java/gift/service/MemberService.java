package gift.service;

import gift.entity.Member;
import gift.repository.MemberRepository;
import gift.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtTokenProvider tokenProvider;
  private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

  @Autowired
  public MemberService(MemberRepository memberRepository, JwtTokenProvider tokenProvider) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.tokenProvider = tokenProvider;
  }
  public String register(String email, String password) {
    if (memberRepository.findByEmail(email).isPresent()){
      throw new RuntimeException("이미 존재하는 이메일입니다.");
    }

    Member member = new Member();
    member.setEmail(email);
    member.setPassword(passwordEncoder.encode(password));
    memberRepository.save(member);
    return tokenProvider.createToken(member.getEmail());
  }

  public String authenticate(String email, String password) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다."));
    if (!passwordEncoder.matches(password, member.getPassword())) {
      throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
    }
    return tokenProvider.createToken(member.getEmail());
  }

  public Member getMemberFromToken(String token) {
    String email = tokenProvider.getEmailFromToken(token);
    return memberRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Invalid token"));
  }
}
