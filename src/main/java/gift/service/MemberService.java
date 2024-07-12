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

    public List<Member> getAllMember() {
        return memberRepository
            .findAll()
            .stream()
            .map(this::entityToDomain)
            .toList();
    }

    public String register(MemberRequest memberRequest) {

        MemberEntity memberEntity = memberRepository.save(dtoToEntity(memberRequest));

        return jwtUtil.generateToken(entityToDomain(memberEntity));
    }

    public String login(MemberRequest memberRequest) {
        MemberEntity memberEntity = memberRepository
            .findByEmail(memberRequest.getEmail())
            .orElseThrow(() -> new EntityNotFoundException("not found Entity"));
        Member member = entityToDomain(memberEntity);

        if (member.getPassword().equals(memberRequest.getPassword())) {
            return jwtUtil.generateToken(member);
        }
        return null;
    }

    public void deleteMember(Long id) {
        MemberEntity memberEntity = memberRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found Entity"));
        memberRepository.delete(memberEntity);
    }

    public Member getMemberFromToken(String token) {
        String email = jwtUtil.getEmailFromToken(token);

        if (email != null) {
            return entityToDomain(memberRepository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("not found Entity"))
            );
        }
        return null;
    }

    private Member entityToDomain(MemberEntity memberEntity){
        return new Member(memberEntity.getId(), memberEntity.getEmail(), memberEntity.getPassword());
    }

    private MemberEntity dtoToEntity(MemberRequest memberRequest){
        return new MemberEntity(memberRequest.getEmail(), memberRequest.getPassword());
    }
}
