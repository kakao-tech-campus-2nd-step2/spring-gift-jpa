package gift.service;

import gift.dto.MemberDTO;
import gift.dto.MemberPasswordDTO;
import gift.exception.AlreadyExistMemberException;
import gift.exception.InvalidPasswordException;
import gift.DAO.MemberDAO;
import gift.util.JwtProvider;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDAO memberDAO;
    private final JwtProvider jwtProvider;

    @Autowired
    public MemberService(MemberDAO memberDAO, JwtProvider jwtProvider) {
        this.memberDAO = memberDAO;
        this.jwtProvider = jwtProvider;
    }

    public MemberDTO findMember(String email) {
        return memberDAO.findMember(email);
    }

    public Map<String, String> register(MemberDTO memberDTO) {
        if (findMember(memberDTO.email()) != null) {
            throw new AlreadyExistMemberException();
        }
        memberDAO.register(memberDTO);
        return Map.of("token:", jwtProvider.createAccessToken(memberDTO));
    }

    public Map<String, String> login(MemberDTO memberDTO) {
        MemberDTO findedmemberDTO = findMember(memberDTO.email());
        checkPassword(memberDTO.password(), findedmemberDTO.password());
        return Map.of("token:", jwtProvider.createAccessToken(memberDTO));
    }

    public Map<String, String> changePassword(MemberDTO memberDTO, MemberPasswordDTO memberPasswordDTO) {
        checkPassword(memberPasswordDTO.newPassword1(), memberDTO.password());
        MemberDTO updatedMemberDTO = new MemberDTO(memberDTO.email(), memberPasswordDTO.newPassword1());
        memberDAO.changePassword(updatedMemberDTO);
        return Map.of("token:", jwtProvider.createAccessToken(updatedMemberDTO));
    }

    private void checkPassword(String password, String expectedPassword) {
        if (!password.equals(expectedPassword)){
            throw new InvalidPasswordException();
        }
    }
}
