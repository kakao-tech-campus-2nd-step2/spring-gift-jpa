package gift.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gift.dto.MemberDto;
import gift.dto.request.LoginRequest;
import gift.entity.Member;
import gift.exception.CustomException;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil){
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public void addMember(MemberDto memberDto){

        if(memberRepository.findByEmail(memberDto.getEmail()).isEmpty()){
            Member member = memberDto.toEntity(memberDto);
            memberRepository.save(member);
        }else{
            throw new CustomException("Member with email " + memberDto.getEmail() + "exists", HttpStatus.CONFLICT);
        }
    }

    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }

    public MemberDto findByEmail(String email){
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException("Member with email " + email + " not found", HttpStatus.NOT_FOUND));
        return member.toDto();
    }

    public MemberDto findByRequest(LoginRequest loginRequest){
        Member member = memberRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
            .orElseThrow(() -> new CustomException("User with Request not found", HttpStatus.NOT_FOUND));
        return member.toDto();
    }

    public String generateToken(String email){
        MemberDto memberDto = findByEmail(email);
        return jwtUtil.generateToken(memberDto);
    }

    public String authenticateMember(LoginRequest loginRequest){
        MemberDto memberDto = findByRequest(loginRequest);
        return generateToken(memberDto.getEmail());
    }
    
}
