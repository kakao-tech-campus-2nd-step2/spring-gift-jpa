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

    public void updateProduct(ProductRequest request) {
        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new ProductNotFoundException("해당 상품 id가 존재하지 않습니다."));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());

        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new ProductNotFoundException("해당 상품 id가 존재하지 않습니다.");
        }
        productRepository.deleteById(id);
    }

    public void deleteProducts(List<Long> ids) {
        productRepository.deleteAllById(ids);
    }


}
