package gift.service;

import gift.exception.user.UserNotFoundException;
import gift.model.Member;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final UserRepository userRepository;

    public MemberService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Member getUserByEmail(String email) {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 email의 계정이 존재하지 않습니다."));
        return member;
    }

    public Member getUserById(Long id) {
        Member member = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 email의 계정이 존재하지 않습니다."));
        return member;
    }
}
