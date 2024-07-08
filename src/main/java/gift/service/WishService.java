package gift.service;

import gift.constants.Messages;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.MemberResponseDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.exception.ProductNotFoundException;
import gift.exception.WishNotFoundException;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public void save(MemberResponseDto memberResponseDto, WishRequestDto request){
        Product product = productRepository.findByName(request.getProductName())
                .orElseThrow(() -> new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_NAME));
        wishRepository.save(new Wish(memberResponseDto.getId(),product.getId(),request.getQuantity()));
    }

    public List<WishResponseDto> findByMemberEmail(MemberResponseDto memberResponseDto){
        return wishRepository.findByMemberId(memberResponseDto.getId())
                .orElseThrow(() -> new WishNotFoundException(Messages.NOT_FOUND_WISH))
                .stream()
                .map(this::convertToWishDto)
                .collect(Collectors.toList());
    }

    public void delete(MemberResponseDto memberResponseDto, Long id){
        wishRepository.findByIdAndMemberId(id, memberResponseDto.getId())
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));
        wishRepository.delete(id);
    }

    public void updateQuantity(MemberResponseDto memberResponseDto, Long id, WishRequestDto request){
        wishRepository.findByIdAndMemberId(id, memberResponseDto.getId())
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));
        wishRepository.updateQuantity(id, request.getQuantity());
    }

    private WishResponseDto convertToWishDto(Wish wish) {
        Product product = productRepository.findById(wish.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_NAME));
        return new WishResponseDto(
                wish.getId(),
                wish.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                wish.getQuantity()
        );
    }
}
