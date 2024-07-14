package gift.dto;

import gift.domain.Product;
import gift.domain.Wish;

public class WishResponseDto {
    private Long id;
    private ProductResponseDto productResponseDto;
    private int quantity;

    public WishResponseDto(Long id, ProductResponseDto productResponseDto, int quantity) {
        this.id = id;
        this.productResponseDto = productResponseDto;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductResponseDto getProductResponseDto() {
        return productResponseDto;
    }

    public void setProductResponseDto(ProductResponseDto productResponseDto) {
        this.productResponseDto = productResponseDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static WishResponseDto from(final Wish wish){
        return new WishResponseDto(wish.getId(), ProductResponseDto.from(wish.getProduct()), wish.getQuantity());
    }
}
