package gift.service;

import gift.dto.request.ProductRequest;
import gift.dto.response.AddedProductIdResponse;
import gift.dto.response.ProductResponse;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.jpaRepo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public AddedProductIdResponse addProduct(ProductRequest addRequest) {
        Long addedId = productRepository.save(new Product(addRequest.getName(), addRequest.getPrice(), addRequest.getImageUrl())).getId();
        return new AddedProductIdResponse(addedId);
    }

    public ProductResponse getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl()))
                .get();
    }

    public boolean updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    public boolean deleteProduct(Long id) {
        return productRepository.deleteProduct(id);
    }

    public Product getProduct(Long productId) {
        return productRepository.getProduct(productId);
    }

}
