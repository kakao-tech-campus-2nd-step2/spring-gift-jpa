package gift.service;

import gift.domain.Product;
import gift.domain.Product.Builder;
import gift.repository.ProductRepository;
import gift.web.dto.request.product.CreateProductRequest;
import gift.web.dto.request.product.UpdateProductRequest;
import gift.web.dto.response.product.CreateProductResponse;
import gift.web.dto.response.product.ReadAllProductsResponse;
import gift.web.dto.response.product.ReadProductResponse;
import gift.web.dto.response.product.UpdateProductResponse;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public CreateProductResponse createProduct(CreateProductRequest request) {
        Product product = request.toEntity();
        return new CreateProductResponse(productRepository.save(product));
    }

    public ReadProductResponse searchProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);
        return ReadProductResponse.fromEntity(product);
    }

    public ReadAllProductsResponse readAllProducts() {
        List<ReadProductResponse> products = productRepository.findAll()
            .stream()
            .map(ReadProductResponse::fromEntity)
            .toList();
        return ReadAllProductsResponse.from(products);
    }

    public UpdateProductResponse updateProduct(Long id, UpdateProductRequest request) {
        if(!productRepository.existsById(id)) {
            throw new NoSuchElementException(id + "에 해당하는 상품이 없습니다.");
        }

        Product product = new Builder()
            .id(id)
            .name(request.getName())
            .price(request.getPrice())
            .imageUrl(request.getImageUrl())
            .build();

        productRepository.update(id, product);

        Product updatedProduct = productRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);
        return UpdateProductResponse.from(updatedProduct);
    }

    /**
     * 상품을 삭제합니다.
     * @param id 상품 아이디
     * @return true : 삭제 성공, false : 삭제 실패
     */
    public boolean deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }
}
