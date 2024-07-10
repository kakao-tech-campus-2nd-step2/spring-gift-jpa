package gift.service;

import gift.database.JdbcMemeberRepository;
import gift.dto.LoginMemberToken;
import gift.dto.MemberDTO;
import gift.exceptionAdvisor.MemberServiceException;
import gift.model.Member;
import gift.model.MemberRole;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private JdbcMemeberRepository jdbcMemeberRepository;

    private AuthenticationTool authenticationTool;

    public MemberServiceImpl(JdbcMemeberRepository jdbcMemeberRepository,
        AuthenticationTool authenticationTool) {
        this.jdbcMemeberRepository = jdbcMemeberRepository;
        this.authenticationTool = authenticationTool;
    }

    @Override
    public void register(MemberDTO memberDTO) {
        if (checkEmailDuplication(memberDTO.getEmail())) {
            throw new MemberServiceException("이메일이 중복됩니다", HttpStatus.FORBIDDEN);
        }

        jdbcMemeberRepository.create(memberDTO.getEmail(), memberDTO.getPassword(),
            MemberRole.COMMON_MEMBER.toString());
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
        Member member = jdbcMemeberRepository.findById(id);
        return new MemberDTO(member.getEmail(), member.getPassword(), member.getRole());
    }


    private boolean checkEmailDuplication(String email) {
        try {
            jdbcMemeberRepository.findByEmail(email);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private Member findByEmail(String email) {
        try {
            return jdbcMemeberRepository.findByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            throw new MemberServiceException("잘못된 로그인 시도입니다.", HttpStatus.FORBIDDEN);
        }
    }
}
