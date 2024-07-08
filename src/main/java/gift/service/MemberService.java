package gift.service;

import gift.model.Member;
import gift.repository.JdbcMemberRepository;
import gift.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private final JdbcMemberRepository memberRepository;
  private final JwtUtil jwtUtil;

  public MemberService(JdbcMemberRepository memberRepository, JwtUtil jwtUtil) {
    this.memberRepository = memberRepository;
    this.jwtUtil = jwtUtil;
  }

  public String register(Member member) {
    Long memberId = memberRepository.save(member);
    return jwtUtil.generateToken(memberId, member.getEmail());
  }

  public String login(String email, String password) {
    return memberRepository.findByEmail(email)
            .filter(member -> member.getPassword().equals(password))
            .map(member -> jwtUtil.generateToken(member.getId(), member.getEmail()))
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
  }
}
