package gift.product.service.dto;

import java.util.List;

public record ProductPageInfo(
        List<ProductInfo> products,
        long totalElements,
        int totalPages,
        int currentPage
) {
    public static ProductPageInfo of(List<ProductInfo> products, long totalElements, int totalPages, int currentPage) {
        return new ProductPageInfo(products, totalElements, totalPages, currentPage);
    }
}
