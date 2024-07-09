package gift.Service;

import gift.Exception.AuthorizedException;
import gift.Exception.LoginException;
import gift.Model.*;
import gift.Repository.ProductRepository;
import gift.Repository.MemberRepository;
import gift.Repository.WishlistRepository;
import gift.Token.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public void add(String token, String name){
        String email = jwtTokenProvider.getEmailFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        Optional<Product> productOptional = productRepository.findByName(name);
        if(memberOptional.isEmpty()) throw new AuthorizedException();
        Product product = productOptional.get();
        Member member = memberOptional.get();
        if(!member.getRole().equals(Role.ADMIN) && !member.getRole().equals(Role.CONSUMER))
            throw new AuthorizedException();

        wishlistRepository.save(new Wishlist(member.getId(), product.getId()));
    }

    public void delete(String token, String name){
        String email = jwtTokenProvider.getEmailFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        Optional<Product> productOptional = productRepository.findByName(name);
        Product product = productOptional.get();
        if(memberOptional.isEmpty())
            throw new AuthorizedException();
        Member member = memberOptional.get();
        if(!member.getRole().equals(Role.ADMIN) && !member.getRole().equals(Role.CONSUMER))
            throw new AuthorizedException();

        wishlistRepository.delete(wishlistRepository.findByMemberIdAndProductId(member.getId(), product.getId()));
    }

    public List<String> viewAll(String token){
        String email = jwtTokenProvider.getEmailFromToken(token);
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if(memberOptional.isEmpty())
            throw new AuthorizedException();
        Member member = memberOptional.get();

        if(!member.getRole().equals(Role.ADMIN) && !member.getRole().equals(Role.CONSUMER))
            throw new AuthorizedException();

        List<Wishlist> wishlists = wishlistRepository.findByMemberId(member.getId());
        List<String> productNames = new ArrayList<>();
        for(Wishlist w : wishlists){
            productNames.add(productRepository.findById(w.getProductId()).get().getName());
        }
        return productNames;
    }
}
