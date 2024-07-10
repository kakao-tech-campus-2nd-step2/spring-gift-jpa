package gift.Service;

import gift.Exception.AuthorizedException;
import gift.Exception.ProductNotFoundException;
import gift.Model.*;
import gift.Repository.ProductRepository;
import gift.Repository.MemberRepository;
import gift.Repository.WishRepository;
import gift.Token.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public WishService(WishRepository wishRepository, MemberRepository memberRepository, ProductRepository productRepository, JwtTokenProvider jwtTokenProvider){
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void add(String token, String name){
        String email = jwtTokenProvider.getEmailFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        Optional<Product> productOptional = productRepository.findByName(name);
        if(memberOptional.isEmpty()) {
            throw new AuthorizedException();
        }

        if(productOptional.isEmpty()){
            throw new ProductNotFoundException();
        }

        Member member = memberOptional.get();
        Product product = productOptional.get();
        if(!member.getRole().equals(Role.ADMIN) && !member.getRole().equals(Role.CONSUMER))
            throw new AuthorizedException();

        wishRepository.save(new Wish(member.getId(), product.getId()));
    }

    public void delete(String token, String name){
        String email = jwtTokenProvider.getEmailFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        Optional<Product> productOptional = productRepository.findByName(name);

        if(memberOptional.isEmpty()) {
            throw new AuthorizedException();
        }

        if(productOptional.isEmpty()){
            throw new ProductNotFoundException();
        }

        Product product = productOptional.get();
        Member member = memberOptional.get();
        if(!member.getRole().equals(Role.ADMIN) && !member.getRole().equals(Role.CONSUMER)) {
            throw new AuthorizedException();
        }

        wishRepository.delete(wishRepository.findByMemberIdAndProductId(member.getId(), product.getId()));
    }

    public List<String> viewAll(String token){
        String email = jwtTokenProvider.getEmailFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if(memberOptional.isEmpty()) {
            throw new AuthorizedException();
        }
        Member member = memberOptional.get();

        if(!member.getRole().equals(Role.ADMIN) && !member.getRole().equals(Role.CONSUMER)) {
            throw new AuthorizedException();
        }

        List<Wish> wishes = wishRepository.findByMemberId(member.getId());
        List<String> productNames = new ArrayList<>();

        for(Wish w : wishes){
            productNames.add(productRepository.findById(w.getProductId()).get().getName());
        }

        return productNames;
    }
}
