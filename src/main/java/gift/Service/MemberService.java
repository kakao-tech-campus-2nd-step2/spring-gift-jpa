package gift.Service;

import gift.Entity.Member;
import gift.Mapper.Mapper;
import gift.Model.MemberDto;
import gift.Repository.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Qualifier("userService")
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;
    private final Mapper mapper;

    @Autowired
    public MemberService(MemberJpaRepository memberJpaRepository, Mapper mapper) {
        this.memberJpaRepository = memberJpaRepository;
        this.mapper = mapper;
    }

    public void register(MemberDto memberDto) {
        Member member = mapper.memberDtoToEntity(memberDto);
        memberJpaRepository.save(member);
    }

    public Optional<MemberDto> findByUserId(long id) {
        return memberJpaRepository.findById(id)
                .map(mapper::memberToDto);
    }

    public MemberDto findByEmail(String email) {
        Optional<Member> member = memberJpaRepository.findByEmail(email);
        return mapper.memberToDto(member.get());
    }

    public boolean isAdmin(String email) {
        Optional<Member> user = memberJpaRepository.findByEmail(email);
        return user.get().isAdmin();
    }

    public boolean authenticate(String email, String password) {
        Optional<Member> user = memberJpaRepository.findByEmailAndPassword(email, password);
        return user != null && user.get().getPassword().equals(password);
    }

}
