package gift.service;

import gift.entity.Product;
import gift.entity.ProductDTO;
import gift.entity.ProductWishlist;
import gift.entity.Wishlist;
import gift.exception.ResourceNotFoundException;
import gift.repository.ProductRepository;
import gift.repository.ProductWishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductWishlistRepository productWishlistRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductWishlistRepository productWishlistRepository) {
        this.productRepository = productRepository;
        this.productWishlistRepository = productWishlistRepository;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public List<Wishlist> getProductWishlist(Long productId) {
        List<ProductWishlist> productWishlists = productWishlistRepository.findByProductId(productId);
        List<Wishlist> wishlists = productWishlists.stream()
                .map(productWishlist -> productWishlist.getWishlist())
                .collect(Collectors.toList());
        return wishlists;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, ProductDTO product) {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        foundProduct.setName(product.getName());
        foundProduct.setPrice(product.getPrice());
        foundProduct.setImageurl(product.getImageurl());
        return productRepository.save(foundProduct);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
