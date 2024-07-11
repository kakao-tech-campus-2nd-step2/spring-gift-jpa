package gift.service;

import gift.dto.user.*;
import gift.entity.Member;
import gift.exception.InvalidPasswordException;
import gift.exception.NoSuchUserException;
import gift.repository.UserRepository;
import gift.security.jwt.TokenProvider;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;


    public UserService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll().stream().map((member) -> new UserResponseDTO(
                member.getId(),
                member.getEmail()
        )).toList();
    }

    public static String hashPassword(String plainPw) {
        return BCrypt.hashpw(plainPw, BCrypt.gensalt());
    }

    public TokenResponseDTO signUp(UserRequestDTO userRequestDTO) {
        String email = userRequestDTO.email();
        String encryptedPW = hashPassword(userRequestDTO.password());

        Member member = userRepository.save(new Member(email, encryptedPW));

        String token = tokenProvider.generateToken(member.getEmail());

        return new TokenResponseDTO(token);
    }

    public TokenResponseDTO login(UserRequestDTO userRequestDTO) throws InvalidPasswordException {
        Member member = userRepository.findByEmail(userRequestDTO.email())
                .orElseThrow(NoSuchUserException::new);

        String encodedOriginalPw = member.getPassword();

        if (!BCrypt.checkpw(userRequestDTO.password(), encodedOriginalPw)) {
            throw new InvalidPasswordException("Invalid password");
        }

        String token = tokenProvider.generateToken(member.getEmail());

        return new TokenResponseDTO(token);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void deleteUser(String email) {
        Member member = userRepository.findByEmail(email)
                        .orElseThrow(NoSuchUserException::new);

        userRepository.deleteById(member.getId());
    }

    public void updatePw(long id, PwUpdateDTO pwUpdateDTO) {
        String encryptedPW = hashPassword(pwUpdateDTO.password());

        Member member = userRepository.findById(id).get();
        member.setPassword(encryptedPW);

        userRepository.save(member);
    }

    public void updatePw(String email, PwUpdateDTO pwUpdateDTO) {
        String encryptedPW = hashPassword(pwUpdateDTO.password());

        Member member = userRepository.findByEmail(email).orElseThrow(NoSuchUserException::new);
        member.setPassword(encryptedPW);

        userRepository.save(member);
    }
}
