package gift.service;

import gift.domain.product.Product;
import gift.domain.product.ProductRepository;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public void addProduct(ProductRequestDto requestDto){
        Product product = new Product(requestDto.getName(), requestDto.getPrice(), requestDto.getImgUrl());
        repository.save(product);
    }

    public List<ProductResponseDto> findAll(){
        return repository.findAll()
                .stream()
                .map(ProductResponseDto::new)
                .toList();
    }

    public ProductResponseDto findProduct(Long id){
        checkValidId(id);
        return new ProductResponseDto(repository.findById(id));
    }

    public ProductResponseDto editProduct(Long id, ProductRequestDto request){
        checkValidId(id);
        repository.update(id,request);
        return new ProductResponseDto(repository.findById(id));
    }

    public void deleteProduct(Long id){
        checkValidId(id);
        repository.deleteById(id);
    }

    public void checkValidId(Long id) {
        if (repository.isNotValidProductId(id)){
            throw new IllegalArgumentException("유효하지 않은 상품 정보입니다.");
        }
    }
}
