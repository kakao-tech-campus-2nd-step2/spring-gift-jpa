package gift.Service;

import gift.Exception.LoginException;
import gift.Model.Role;
import gift.Model.DTO.MemberDTO;
import gift.Model.Entity.MemberEntity;
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

        public JwtToken register(MemberDTO memberDTO){
            if(memberRepository.findByEmail(memberDTO.email()).isPresent())
                throw new LoginException();

            MemberEntity memberEntity = new MemberEntity(memberDTO.email(), memberDTO.password(), Role.CONSUMER);
            memberRepository.save(memberEntity);
            return new JwtToken(jwtTokenProvider.createToken(memberEntity));
        }

        public JwtToken login(MemberDTO memberDTO){
            Optional<MemberEntity> member = memberRepository.findByEmail(memberDTO.email());

            if(member.isEmpty() || !member.get().getPassword().equals(memberDTO.password()))
                throw new LoginException();

            return new JwtToken(jwtTokenProvider.createToken(member.get()));
        }
}
