package gift.member.business.service;

import gift.global.authentication.jwt.JwtGenerator;
import gift.global.authentication.jwt.JwtToken;
import gift.global.exception.ErrorCode;
import gift.global.exception.LoginException;
import gift.member.business.dto.MemberRegisterDto;
import gift.member.business.dto.WishListDto;
import gift.member.persistence.entity.Wishlist;
import gift.member.persistence.repository.MemberRepository;
import gift.member.business.dto.MemberLoginDto;
import gift.member.persistence.repository.WishlistRepository;
import gift.member.presentation.dto.WishlistUpdateDto;
import gift.product.persistence.entity.Product;
import gift.product.persistence.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtGenerator jwtGenerator;

    public MemberService(MemberRepository memberRepository, JwtGenerator jwtGenerator) {
        this.memberRepository = memberRepository;
        this.jwtGenerator = jwtGenerator;
    }

    public JwtToken registerMember(MemberRegisterDto memberRegisterDto) {
        var member = memberRegisterDto.toMember();
        var id = memberRepository.saveMember(member);
        return jwtGenerator.createToken(id);
    }

    public JwtToken loginMember(MemberLoginDto memberLoginDto) {
        var member = memberRepository.getMemberByEmail(memberLoginDto.email());
        if (!member.getPassword().equals(memberLoginDto.password())) {
            throw new LoginException(ErrorCode.LOGIN_ERROR, "패스워드가 이메일과 일치하지 않습니다.");
        }
        return jwtGenerator.createToken(member.getId());
    }
}
