package gift.service;

import gift.common.exception.EntityNotFoundException;
import gift.controller.dto.request.MemberRequest;
import gift.controller.dto.response.MemberResponse;
import gift.model.Member;
import gift.repository.MemberDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void save(MemberRequest request) {
        memberDao.save(request.email(), request.password(), request.role());
    }

    public List<MemberResponse> findAll() {
        return memberDao.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse findById(Long id) {
        Member member = memberDao.findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException("Member with id " + id + " not found"));
        return MemberResponse.from(member);
    }

    public void updateById(Long id, MemberRequest request) {
        memberDao.updatedById(id, request);
    }

    public void deleteById(Long id) {
        memberDao.deleteById(id);
    }
}
