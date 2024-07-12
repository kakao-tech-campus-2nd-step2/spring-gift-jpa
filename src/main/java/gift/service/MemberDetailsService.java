package gift.service;

import gift.repository.MemberJpaRepository;
import gift.web.dto.MemberDetails;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    public MemberDetailsService(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    public MemberDetails loadUserById(Long id) {
        return MemberDetails.from(memberJpaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + id)));
    }
}
