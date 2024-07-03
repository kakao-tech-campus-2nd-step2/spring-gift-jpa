package gift.product.dto;

import java.util.Objects;

public record ProductRequestDto(String name, int price, String imageUrl) {
    public ProductRequestDto {
        Objects.requireNonNull(name);
        Objects.requireNonNull(imageUrl);
    }

    public ServiceDto toServiceDto() {
        return new ServiceDto(null, this.name, this.price, this.imageUrl);
    }

    public ServiceDto toServiceDto(Long id) {
        return new ServiceDto(id, this.name, this.price, this.imageUrl);
    }
}
