package gift.service;

import gift.dto.member.*;
import gift.entity.Member;
import gift.exception.DuplicatedEmailException;
import gift.exception.InvalidPasswordException;
import gift.exception.NoSuchMemberException;
import gift.repository.MemberRepository;
import gift.security.jwt.TokenProvider;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;


    public MemberService(MemberRepository memberRepository, TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    public List<MemberResponseDTO> getAllUsers() {

        return memberRepository.findAll().stream().map((member) -> new MemberResponseDTO(
                member.getId(),
                member.getEmail()
        )).toList();
    }

    public static String hashPassword(String plainPw) {
        return BCrypt.hashpw(plainPw, BCrypt.gensalt());
    }

    public TokenResponseDTO signUp(MemberRequestDTO memberRequestDTO) {
        String email = memberRequestDTO.email();
        String encryptedPW = hashPassword(memberRequestDTO.password());

        memberRepository.findByEmail(email).ifPresent((member) -> {
            throw new DuplicatedEmailException("Email already exists");
        });

        Member member = memberRepository.save(new Member(email, encryptedPW));

        String token = tokenProvider.generateToken(member.getEmail());

        return new TokenResponseDTO(token);
    }

    public TokenResponseDTO login(MemberRequestDTO memberRequestDTO) throws InvalidPasswordException {
        Member member = memberRepository.findByEmail(memberRequestDTO.email())
                .orElseThrow(NoSuchMemberException::new);

        String encodedOriginalPw = member.getPassword();

        if (!BCrypt.checkpw(memberRequestDTO.password(), encodedOriginalPw)) {
            throw new InvalidPasswordException("Invalid password");
        }

        String token = tokenProvider.generateToken(member.getEmail());

        return new TokenResponseDTO(token);
    }

    public void deleteUser(long id) {
        memberRepository.deleteById(id);
    }

    public void deleteUser(String email) {
        Member member = memberRepository.findByEmail(email)
                        .orElseThrow(NoSuchMemberException::new);

        memberRepository.delete(member);
    }

    public void updatePw(long id, PwUpdateDTO pwUpdateDTO) {
        String encryptedPW = hashPassword(pwUpdateDTO.password());

        Member member = memberRepository.findById(id).get();
        member.setPassword(encryptedPW);

        memberRepository.save(member);
    }

    public void updatePw(String email, PwUpdateDTO pwUpdateDTO) {
        String encryptedPW = hashPassword(pwUpdateDTO.password());

        Member member = memberRepository.findByEmail(email).orElseThrow(NoSuchMemberException::new);
        member.setPassword(encryptedPW);

        memberRepository.save(member);
    }
}
