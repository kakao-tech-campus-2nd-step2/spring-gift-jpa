package gift.service;

import gift.dto.ProductRegisterRequestDto;
import gift.domain.Product;
import gift.dto.ProductResponseDto;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public ProductResponseDto getProductById(long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("해당 id의 상품 없음: " + id));;
        return new ProductResponseDto(product.getName(), product.getPrice(), product.getImageUrl());
    }

    public Long addProduct(ProductRegisterRequestDto productDto){
        Product newProduct = new Product(productDto.getName(),productDto.getPrice(),productDto.getImageUrl());
        Product savedProduct = productRepository.save(newProduct);
        return savedProduct.getId();
    }

    public Long updateProduct(long id, ProductRegisterRequestDto productDto){
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("해당 id의 상품 없음: " + id));

        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setImageUrl(productDto.getImageUrl());

        Product savedProduct = productRepository.save(existingProduct);
        return savedProduct.getId();
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Page<ProductResponseDto> getPagedProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(this::convertToDto);
    }

    private ProductResponseDto convertToDto(Product product) {
        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }
}
