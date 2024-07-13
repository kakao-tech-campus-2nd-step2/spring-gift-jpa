package gift.service;

import gift.dto.TokenLoginRequestDTO;
import gift.dto.WishResponseDTO;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.WishException;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public WishService(WishRepository wishRepository,
                       ProductRepository productRepository,
                       MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public Long findByEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()){
            return member.get()
                    .getId();
        }
        throw new WishException("User not found");
    }

    @Transactional
    public void addWish(Long productId, TokenLoginRequestDTO loginUser) {
        String email = loginUser.getEmail();
        Long memberId = findByEmail(email);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new WishException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new WishException("Product not found"));

        Optional<Wish> existingWish = wishRepository.findByMemberIdAndProductId(memberId, productId);
        if (existingWish.isPresent()) {
            Wish wish = existingWish.get();
            wish.incrementCount();
            wishRepository.save(wish);
            return;
        }
        Wish wish = new Wish(member, product);
        wishRepository.save(wish);
    }

    @Transactional
    public void removeWish(long productId, TokenLoginRequestDTO loginUser) {
        String email = loginUser.getEmail();
        Long memberId = findByEmail(email);

        Wish wish = wishRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new WishException("Product not found"));

        if (wish.getCount() > 1) {
            wish.decrementCount();
            wishRepository.save(wish);
            return;
        }
        wishRepository.delete(wish);

    }


    public List<WishResponseDTO> WishToDto(List<Wish> wishlist){
        List<WishResponseDTO> wishResponseDtoList = new ArrayList<>();
        for(Wish wish : wishlist){
            Product product = wish.getProduct();
            Member member = wish.getMember();
            WishResponseDTO wishResponseDTO = new WishResponseDTO(
                    member.getEmail(),
                    product.getName(),
                    product.getImageUrl(),
                    wish.getCount(),
                    product.getPrice()
            );
            wishResponseDtoList.add(wishResponseDTO);
        }
        return wishResponseDtoList;
    }

    public List<WishResponseDTO> getWishesByMemberId(TokenLoginRequestDTO tokenLoginRequestDTO) {
        String email = tokenLoginRequestDTO.getEmail();
        Long memberId = findByEmail(email);
        List<Wish> wishlist = wishRepository.findByMemberId(memberId);
        return WishToDto(wishlist);
    }


    public Page<WishResponseDTO> paging(Pageable pageable){

        int page = pageable.getPageNumber() - 1;
        int pageSize = pageable.getPageSize();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Page<Wish> wishesPage = wishRepository.findAll(PageRequest.of(page, pageSize, sort));

        Page<WishResponseDTO> wishResponseDTOs = wishesPage.map(wish -> {
            return new WishResponseDTO(
                    wish.getMember().getEmail(),
                    wish.getProduct().getName(),
                    wish.getProduct().getImageUrl(),
                    wish.getCount(),
                    wish.getProduct().getPrice()
            );
        });

        return wishResponseDTOs;
    }

}