package gift.service;

import gift.model.Product;
import gift.dto.ProductDTO;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;

    public ProductService(ProductRepository productRepository,
        WishlistRepository wishlistRepository) {
        this.productRepository = productRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findProductsById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveProduct(ProductDTO productDTO) {
        productRepository.save(toEntity(productDTO, null));
    }

    @Transactional
    public void updateProduct(ProductDTO productDTO, Long id) {
        productRepository.save(toEntity(productDTO, id));
    }

    @Transactional
    public void deleteProductAndWishlist(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        wishlistRepository.deleteByProduct(product);
        productRepository.delete(product);
    }

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getName(), String.valueOf(product.getPrice()),
            product.getImageUrl());
    }

    public static Product toEntity(ProductDTO productDTO, Long id) {
        Product product = new Product(id, productDTO.name(), productDTO.price(),
            productDTO.imageUrl());
        return product;
    }
}
