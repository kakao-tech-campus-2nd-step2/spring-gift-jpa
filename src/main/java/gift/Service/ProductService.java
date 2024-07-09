package gift.Service;

import gift.Exception.AuthorizedException;
import gift.Model.Product;
import gift.Model.Member;
import gift.Model.Role;
import gift.Repository.ProductRepository;
import gift.Repository.MemberRepository;
import gift.Token.JwtTokenProvider;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ProductService(ProductRepository productRepository, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider){
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void add(String token, Product product){
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if(memberOptional.isEmpty()) throw new AuthorizedException();

        Member member = memberOptional.get();
        if(member.getRole() != Role.ADMIN)
            throw new AuthorizedException();

        productRepository.save(product);
    }

    public void delete(String token, Long id){
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if(memberOptional.isEmpty()) throw new AuthorizedException();

        Member member = memberOptional.get();
        if(member.getRole() != Role.ADMIN)
            throw new AuthorizedException();

        productRepository.deleteById(id);
    }

    public void edit(String token, Long id, Product product){
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if(memberOptional.isEmpty()) throw new AuthorizedException();

        Member member = memberOptional.get();
        if(member.getRole() != Role.ADMIN)
            throw new AuthorizedException();

        productRepository.save(product);
    }

    public List<Product> getAll(String token){
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if(memberOptional.isEmpty()) throw new AuthorizedException();

        Member member = memberOptional.get();
        if(!member.getRole().equals(Role.ADMIN) && !member.getRole().equals(Role.CONSUMER))
            throw new AuthorizedException();

        return productRepository.findAll();
    }

    public Optional<Product> getById(String token, Long id){
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if(memberOptional.isEmpty()) throw new AuthorizedException();

        Member member = memberOptional.get();
        if(!member.getRole().equals(Role.ADMIN) && !member.getRole().equals(Role.CONSUMER))
            throw new AuthorizedException();

        return productRepository.findById(id);
    }


}
