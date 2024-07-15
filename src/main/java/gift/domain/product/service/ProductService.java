package gift.domain.product.service;

import gift.domain.product.dao.ProductJpaRepository;
import gift.domain.product.dto.ProductDto;
import gift.domain.product.entity.Product;
import gift.domain.wishlist.service.WishlistService;
import gift.exception.InvalidProductInfoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final WishlistService wishlistService;

    public ProductService(ProductJpaRepository productJpaRepository,
        WishlistService wishlistService) {
        this.productJpaRepository = productJpaRepository;
        this.wishlistService = wishlistService;
    }

    public Product create(ProductDto productDto) {
        Product product = productDto.toProduct();
        return productJpaRepository.save(product);
    }

    public Page<Product> readAll(Pageable pageable) {
        return productJpaRepository.findAll(pageable);
    }

    public Product readById(long productId) {
        return productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
    }

    public Product update(long productId, ProductDto productDto) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        product.updateInfo(productDto.name(), productDto.price(), productDto.imageUrl());
        return productJpaRepository.save(product);
    }

    public void delete(long productId) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        wishlistService.deleteAllByProductId(productId);
        productJpaRepository.delete(product);
    }
}
