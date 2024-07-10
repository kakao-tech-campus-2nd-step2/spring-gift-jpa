package gift.service;

import gift.entity.MemberEntity;
import gift.domain.Token;
import gift.domain.Member;
import gift.error.ForbiddenException;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import gift.util.PasswordUtil;
import gift.error.AlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public Token register(Member member) {
        MemberEntity existingMemberEntity = memberRepository.findByEmail(member.getEmail());
        if (existingMemberEntity != null) {
            throw new AlreadyExistsException("Alreay Exists Member");
        }
        member.setPassword(PasswordUtil.encodePassword(member.getPassword()));
        memberRepository.save(dtoToEntity(member));

        MemberEntity memberEntity = memberRepository.findByEmail(member.getEmail());
        return new Token(jwtUtil.generateToken(entityToDto(memberEntity)));
    }

    public Token login(Member member) {
        MemberEntity existingMemberEntity = memberRepository.findByEmail(member.getEmail());
        if (existingMemberEntity != null &&
            PasswordUtil.decodePassword(existingMemberEntity.getPassword()).equals(member.getPassword())) {
            return new Token(jwtUtil.generateToken(member));
        }
        throw new ForbiddenException("Invalid email or password");
    }

    private Member entityToDto(MemberEntity memberEntity) {
        return new Member(memberEntity.getEmail(), memberEntity.getPassword());
    }

    private MemberEntity dtoToEntity(Member member) {
        return new MemberEntity(member.getEmail(), member.getPassword());
    }

}
