package gift.service;

import gift.common.exception.DuplicateDataException;
import gift.controller.dto.request.SignUpRequest;
import gift.repository.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    private final MemberDao memberDao;

    public SignUpService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void signUp(SignUpRequest request) {
        if (memberDao.existsByEmail(request.email())) {
            throw new DuplicateDataException("Email already exists", "Duplicate Email");
        }
        memberDao.save(request.email(), request.password());
    }
}
