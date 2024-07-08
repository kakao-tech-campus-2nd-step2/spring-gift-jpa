package gift.service;

import gift.model.dto.MemberRequestDto;
import gift.repository.MemberDao;

public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void insertMember(MemberRequestDto memberRequestDto) {
        memberDao.insertMember(memberRequestDto.toEntity());
    }
}
