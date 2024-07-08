package gift.service;

import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.UserResponseDto;
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

    private final String NOT_FOUND_PRODUCT_BY_NAME_MESSAGE = "해당 이름의 상품이 존재하지 않습니다.";
    private final String NOT_FOUND_WISH_MESSAGE = "위시가 존재하지 않습니다.";

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public void save(UserResponseDto userResponseDto, WishRequestDto request){
        Product product = productRepository.findByName(request.getProductName())
                .orElseThrow(() -> new ProductNotFoundException(NOT_FOUND_PRODUCT_BY_NAME_MESSAGE));
        wishRepository.save(new Wish(userResponseDto.getId(),product.getId(),request.getQuantity()));
    }

    public List<WishResponseDto> findByUserEmail(UserResponseDto userResponseDto){
        return wishRepository.findByUserId(userResponseDto.getId())
                .orElseThrow(() -> new WishNotFoundException(NOT_FOUND_WISH_MESSAGE))
                .stream()
                .map(this::convertToWishDto)
                .collect(Collectors.toList());
    }

    public void delete(UserResponseDto userResponseDto, Long id){
        wishRepository.findByIdAndUserId(id, userResponseDto.getId())
                .orElseThrow(()-> new WishNotFoundException(NOT_FOUND_WISH_MESSAGE));
        wishRepository.delete(id);
    }

    public void updateQuantity(UserResponseDto userResponseDto, Long id, WishRequestDto request){
        wishRepository.findByIdAndUserId(id, userResponseDto.getId())
                .orElseThrow(()-> new WishNotFoundException(NOT_FOUND_WISH_MESSAGE));
        wishRepository.updateQuantity(id, request.getQuantity());
    }

    private WishResponseDto convertToWishDto(Wish wish) {
        Product product = productRepository.findById(wish.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(NOT_FOUND_PRODUCT_BY_NAME_MESSAGE));
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
