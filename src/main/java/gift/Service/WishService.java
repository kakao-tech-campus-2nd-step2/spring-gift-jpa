package gift.Service;

import gift.Exception.AuthorizedException;
import gift.Exception.ProductNotFoundException;
import gift.Model.*;
import gift.Model.DTO.ProductDTO;
import gift.Model.Entity.MemberEntity;
import gift.Model.Entity.ProductEntity;
import gift.Model.Entity.WishEntity;
import gift.Repository.ProductRepository;
import gift.Repository.MemberRepository;
import gift.Repository.WishRepository;
import gift.Token.JwtTokenProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, MemberRepository memberRepository, ProductRepository productRepository){
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public void add(String email, String name){
        Optional<MemberEntity> memberOptional = memberRepository.findByEmail(email);
        Optional<ProductEntity> productOptional = productRepository.findByName(name);
        if(memberOptional.isEmpty()) {
            throw new AuthorizedException();
        }

        if(productOptional.isEmpty()){
            throw new ProductNotFoundException();
        }

        MemberEntity memberEntity = memberOptional.get();
        ProductEntity productEntity = productOptional.get();
        if(!memberEntity.getRole().equals(Role.ADMIN) && !memberEntity.getRole().equals(Role.CONSUMER))
            throw new AuthorizedException();

        wishRepository.save(new WishEntity(memberEntity, productEntity));
    }

    public void delete(String email, String name){
        Optional<MemberEntity> memberOptional = memberRepository.findByEmail(email);
        Optional<ProductEntity> productOptional = productRepository.findByName(name);

        if(memberOptional.isEmpty()) {
            throw new AuthorizedException();
        }

        if(productOptional.isEmpty()){
            throw new ProductNotFoundException();
        }

        ProductEntity productEntity = productOptional.get();
        MemberEntity memberEntity = memberOptional.get();
        if(!memberEntity.getRole().equals(Role.ADMIN) && !memberEntity.getRole().equals(Role.CONSUMER)) {
            throw new AuthorizedException();
        }

        wishRepository.delete(wishRepository.findByMemberIdAndProductId(memberEntity.getId(), productEntity.getId()));
    }

    public List<String> getAll(String email){
        Optional<MemberEntity> memberOptional = memberRepository.findByEmail(email);

        if(memberOptional.isEmpty()) {
            throw new AuthorizedException();
        }
        MemberEntity memberEntity = memberOptional.get();

        if(!memberEntity.getRole().equals(Role.ADMIN) && !memberEntity.getRole().equals(Role.CONSUMER)) {
            throw new AuthorizedException();
        }

        List<WishEntity> wishEntities = wishRepository.findByMemberId(memberEntity.getId());
        List<String> productNames = new ArrayList<>();

        for(WishEntity w : wishEntities){
            productNames.add(productRepository.findById(w.getProduct().getId()).get().getName());
        }

        return productNames;
    }

    public Page<String> getPage(String email, int page){
        List<String> dtoList = getAll(email);
        Pageable pageable = PageRequest.of(page, 10);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtoList.size());

        List<String> subList = dtoList.subList(start, end);

        return new PageImpl<>(subList, pageable, dtoList.size());
    }
}
