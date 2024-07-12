package gift.Service;

import gift.Exception.AuthorizedException;
import gift.Exception.ProductNotFoundException;
import gift.Model.DTO.ProductDTO;
import gift.Model.Entity.ProductEntity;
import gift.Model.Entity.MemberEntity;
import gift.Model.Role;
import gift.Repository.ProductRepository;
import gift.Repository.MemberRepository;
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
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ProductService(ProductRepository productRepository, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider){
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void add(String token, ProductDTO productDTO){
        String email = jwtTokenProvider.getEmailFromToken(token);

        Optional<MemberEntity> memberOptional = memberRepository.findByEmail(email);
        if(memberOptional.isEmpty()) throw new AuthorizedException();

        MemberEntity memberEntity = memberOptional.get();
        if(memberEntity.getRole() != Role.ADMIN)
            throw new AuthorizedException();

        productRepository.save(new ProductEntity(productDTO.name(), productDTO.price(), productDTO.imageUrl()));
    }

    public void delete(String token, Long id){
        String email = jwtTokenProvider.getEmailFromToken(token);

        Optional<MemberEntity> memberOptional = memberRepository.findByEmail(email);
        if(memberOptional.isEmpty()) throw new AuthorizedException();

        MemberEntity memberEntity = memberOptional.get();
        if(memberEntity.getRole() != Role.ADMIN)
            throw new AuthorizedException();

        productRepository.deleteById(id);
    }

    public void edit(String token, Long id, ProductDTO productDTO){
        String email = jwtTokenProvider.getEmailFromToken(token);

        Optional<MemberEntity> memberOptional = memberRepository.findByEmail(email);
        if(memberOptional.isEmpty()) throw new AuthorizedException();

        MemberEntity memberEntity = memberOptional.get();
        if(memberEntity.getRole() != Role.ADMIN)
            throw new AuthorizedException();
        ProductEntity productEntity = new ProductEntity(productDTO.name(), productDTO.price(), productDTO.imageUrl());
        productEntity.setId(id);

        productRepository.save(productEntity);
    }

    public List<ProductDTO> getAll(String token){
        String email = jwtTokenProvider.getEmailFromToken(token);

        Optional<MemberEntity> memberOptional = memberRepository.findByEmail(email);
        if(memberOptional.isEmpty()) throw new AuthorizedException();

        MemberEntity memberEntity = memberOptional.get();
        if(!memberEntity.getRole().equals(Role.ADMIN) && !memberEntity.getRole().equals(Role.CONSUMER))
            throw new AuthorizedException();
        List<ProductEntity> entityList = productRepository.findAll();
        List<ProductDTO> dtoList = new ArrayList<>();

        for(ProductEntity p: entityList){
            dtoList.add(new ProductDTO(p.getId(), p.getName(), p.getPrice(), p.getImageUrl()));
        }

        return dtoList;
    }

    public Page<ProductDTO> transferListToPage(List<ProductDTO> dtoList, int page){
        Pageable pageable = PageRequest.of(page, 10);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtoList.size());
        List<ProductDTO> subList = dtoList.subList(start, end);

        return new PageImpl<>(subList, pageable, dtoList.size());
    }

    public ProductDTO getById(String token, Long id){
        String email = jwtTokenProvider.getEmailFromToken(token);

        Optional<MemberEntity> memberOptional = memberRepository.findByEmail(email);
        if(memberOptional.isEmpty()) throw new AuthorizedException();

        MemberEntity memberEntity = memberOptional.get();
        if(!memberEntity.getRole().equals(Role.ADMIN) && !memberEntity.getRole().equals(Role.CONSUMER))
            throw new AuthorizedException();

        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);

        if(productEntityOptional.isEmpty()){
            throw new ProductNotFoundException();
        }
        ProductEntity productEntity = productEntityOptional.get();

        return new ProductDTO(productEntity.getId(), productEntity.getName(), productEntity.getPrice(), productEntity.getImageUrl());
    }


}
