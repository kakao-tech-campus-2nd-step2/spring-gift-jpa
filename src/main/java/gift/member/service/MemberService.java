package gift.member.service;

import gift.member.domain.MemberEntity;
import gift.member.domain.Token;
import gift.member.domain.Member;
import gift.member.error.ForbiddenException;
import gift.member.repository.MemberRepository;
import gift.member.util.JwtUtil;
import gift.member.util.PasswordUtil;
import gift.product.domain.Product;
import gift.product.domain.ProductEntity;
import gift.product.error.AlreadyExistsException;
import gift.product.error.NotFoundException;
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
