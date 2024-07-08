package gift.service;

import gift.model.Member;
import gift.model.dto.LoginMemberDto;
import gift.model.dto.MemberRequestDto;
import gift.repository.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public LoginMemberDto selectLoginMemberById(Long id) {
        Member member = memberDao.selectMemberById(id);
        return new LoginMemberDto(member.getId(), member.getName(), member.getEmail(), member.getRole());
    }

    public void insertMember(MemberRequestDto memberRequestDto) {
        memberDao.insertMember(memberRequestDto.toEntity());
    }
}
