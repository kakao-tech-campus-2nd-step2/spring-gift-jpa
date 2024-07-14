package gift.service;

import gift.dto.MemberRequest;
import gift.dto.MemberResponse;
import gift.dto.WishResponse;
import gift.entity.Member;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(MemberRequest memberRequest) {
        if (memberRepository.findByEmail(memberRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        String encodedPassword = passwordEncoder.encode(memberRequest.getPassword());
        Member member = new Member(memberRequest.getEmail(), encodedPassword);
        memberRepository.save(member);
        return jwtUtil.generateToken(member.getId(), member.getEmail(), "USER");
    }

    public String authenticate(MemberRequest memberRequest) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberRequest.getEmail());
        if (optionalMember.isPresent() && passwordEncoder.matches(memberRequest.getPassword(), optionalMember.get().getPassword())) {
            Member member = optionalMember.get();
            return jwtUtil.generateToken(member.getId(), member.getEmail(), "USER");
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        List<WishResponse> wishResponses = member.getWishes().stream()
                .map(wish -> new WishResponse(wish.getId(), wish.getProduct().getId(), wish.getProduct().getName(), wish.getProductNumber()))
                .collect(Collectors.toList());
        return new MemberResponse(member.getId(), member.getEmail(), wishResponses);
    }
}
