package gift.Service;

import gift.Exception.LoginException;
import gift.Model.Role;
import gift.Model.User;
import gift.Model.Member;
import gift.Repository.MemberRepository;
import gift.Token.JwtToken;
import gift.Token.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
        private final MemberRepository memberRepository;
        private final JwtTokenProvider jwtTokenProvider;

        public UserService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider){
            this.memberRepository = memberRepository;
            this.jwtTokenProvider = jwtTokenProvider;
        }

        public JwtToken register(User user){
            if(memberRepository.findByEmail(user.email()).isPresent())
                throw new LoginException();

            Member member = new Member(user.email(), user.password(), Role.CONSUMER);
            memberRepository.save(member);
            return new JwtToken(jwtTokenProvider.createToken(member));
        }

        public JwtToken login(User user){
            Optional<Member> member = memberRepository.findByEmail(user.email());

            if(member.isEmpty() || !member.get().getPassword().equals(user.password()))
                throw new LoginException();

            return new JwtToken(jwtTokenProvider.createToken(member.get()));
        }
}
