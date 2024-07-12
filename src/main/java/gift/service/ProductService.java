package gift.service;

import gift.dto.response.AddedProductIdResponse;
import gift.dto.response.ProductResponse;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public AddedProductIdResponse addProduct(String name, int price, String imageUrl) {
        Long addedProductId = productRepository.save(new Product(name, price, imageUrl)).getId();
        return new AddedProductIdResponse(addedProductId);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Page<ProductResponse> getProductResponses(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::fromProduct);

    }

    @Transactional
    public void updateProduct(Long id, String name, int price, String imageUrl) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        product.change(name, price, imageUrl);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
    }

    @Transactional
    public void deleteProducts(List<Long> ids) {
        productRepository.deleteAllById(ids);
    }

}
