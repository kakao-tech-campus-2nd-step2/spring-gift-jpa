package gift.service;

import gift.domain.Member;
import gift.domain.Token;
import gift.entity.MemberEntity;
import gift.error.AlreadyExistsException;
import gift.error.ForbiddenException;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import gift.util.PasswordUtil;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public Token register(Member member) {
        Optional<MemberEntity> existingMemberEntity = memberRepository.findByEmail(member.getEmail());
        if (existingMemberEntity.isPresent()) {
            throw new AlreadyExistsException("Alreay Exists Member");
        }
        member.setPassword(PasswordUtil.encodePassword(member.getPassword()));
        memberRepository.save(dtoToEntity(member));

        Optional<MemberEntity> loginMemberEntity = memberRepository.findByEmail(member.getEmail());
        return new Token(jwtUtil.generateToken(entityToDto(loginMemberEntity)));
    }

    @Transactional
    public Token login(Member member) {
        Optional<MemberEntity> existingMemberEntity = memberRepository.findByEmail(member.getEmail());
        if (existingMemberEntity.isPresent() && PasswordUtil.matches(member.getPassword(), existingMemberEntity.get().getPassword())) {
            return new Token(jwtUtil.generateToken(entityToDto(existingMemberEntity)));
        }
        throw new ForbiddenException("Invalid email or password");
    }

    private Member entityToDto(Optional<MemberEntity> memberEntity) {
        return new Member(memberEntity.get().getId(), memberEntity.get().getEmail(),
            memberEntity.get().getPassword());
    }

    private MemberEntity dtoToEntity(Member member) {
        return new MemberEntity(member.getEmail(), member.getPassword());
    }

}
