package gift.service;

import gift.dto.LoginMemberToken;
import gift.dto.MemberDTO;

public interface MemberService {

    void register(MemberDTO memberDTO);

    LoginMemberToken login(MemberDTO memberDTO);

    boolean checkRole(MemberDTO memberDTO);

    MemberDTO getLoginUser(String token);
}
