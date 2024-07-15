package gift.service;


import gift.database.JpaMemberRepository;
import gift.dto.LoginMemberToken;
import gift.dto.MemberDTO;
import gift.exceptionAdvisor.MemberServiceException;
import gift.model.Member;
import java.util.NoSuchElementException;
import gift.model.MemberRole;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {


    private JpaMemberRepository jpaMemberRepository;

    private AuthenticationTool authenticationTool;

    public MemberServiceImpl(JpaMemberRepository jpaMemberRepository,
        AuthenticationTool authenticationTool) {
        this.jpaMemberRepository = jpaMemberRepository;
        this.authenticationTool = authenticationTool;
    }

    @Override
    public void register(MemberDTO memberDTO) {
        if (checkEmailDuplication(memberDTO.getEmail())) {
            throw new MemberServiceException("이메일이 중복됩니다", HttpStatus.FORBIDDEN);
        }
        Member member = new Member(null, memberDTO.getEmail(), memberDTO.getPassword(), memberDTO.getRole());
        jpaMemberRepository.save(member);

    }

    @Override
    public LoginMemberToken login(MemberDTO memberDTO) {
        Member member = findByEmail(memberDTO.getEmail());

        if (memberDTO.getPassword().equals(member.getPassword())) {
            String token = authenticationTool.makeToken(member);
            return new LoginMemberToken(token);
        }

        throw new MemberServiceException("잘못된 로그인 시도입니다.", HttpStatus.FORBIDDEN);
    }

    @Override
    public boolean checkRole(MemberDTO memberDTO) {
        return false;
    }

    @Override
    public MemberDTO getLoginUser(String token) {
        long id = authenticationTool.parseToken(token);

        Member member = jpaMemberRepository.findById(id).orElseThrow(()->
            new MemberServiceException("잘못된 로그인 시도입니다",HttpStatus.FORBIDDEN));

        return new MemberDTO(member.getEmail(), member.getPassword(), member.getRole());
    }


    private boolean checkEmailDuplication(String email) {
        try {

            jpaMemberRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
            return true;
        } catch (NoSuchElementException e) {

            return false;
        }
    }

    private Member findByEmail(String email) {
        try {
            return jpaMemberRepository.findByEmail(email).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new MemberServiceException("잘못된 로그인 시도입니다.", HttpStatus.FORBIDDEN);
        }
    }
}
