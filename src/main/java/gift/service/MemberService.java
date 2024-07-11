package gift.service;

import gift.domain.Member;
import gift.dto.MemberRequest;
import gift.entity.MemberEntity;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }
    public List<Member> findAllMember(){
        return memberRepository.findAll().stream().map(MemberEntity::toMember).collect(Collectors.toList());
    }
    public String register(MemberRequest memberRequest) {

        MemberEntity memberEntity = memberRepository.save(memberRequest.toMemberEntity());

        return jwtUtil.generateToken(memberEntity.toMember());
    }

    public String login(MemberRequest memberRequest) {
        MemberEntity memberEntity = memberRepository.findByEmail(memberRequest.getEmail()).orElseThrow(()->new EntityNotFoundException("not found Entity"));
        Member member = memberEntity.toMember();

        if (member.getPassword().equals(memberRequest.getPassword())) {
            return jwtUtil.generateToken(member);
        }
        return null;
    }

    public void deleteMember(Long id){
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found Entity"));
        memberRepository.delete(memberEntity);
    }

    public Member getMemberFromToken(String token) {
        String email = jwtUtil.getEmailFromToken(token);

        if(email != null) {
            return memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("not found Entity")).toMember();
        }
        return null;
    }
}
