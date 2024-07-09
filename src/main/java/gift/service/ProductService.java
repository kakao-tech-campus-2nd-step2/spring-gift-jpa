package gift.service;

import gift.dto.response.AddedProductIdResponse;
import gift.dto.response.ProductResponse;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public AddedProductIdResponse addProduct(String name, int price, String imageUrl) {
        Long addedProductId = productRepository.save(new Product(name, price, imageUrl)).getId();
        return new AddedProductIdResponse(addedProductId);
    }

    public ProductResponse getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl()))
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl()))
                .toList();
    }

    public void updateProduct(Long id, String name, int price, String imageUrl) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        product.setName(name);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
    }

    public void deleteProducts(List<Long> ids) {
        productRepository.deleteAllById(ids);
    }

}
