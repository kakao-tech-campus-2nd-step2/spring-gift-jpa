package gift.product.service;

import gift.global.dto.PageRequestDto;
import gift.global.utility.SortingStateUtility;
import gift.product.dto.ProductRequestDto;
import gift.product.dto.ProductResponseDto;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    // repository를 호출해서 productDTO를 DB에 넣는 함수
    public void insertProduct(ProductRequestDto productRequestDto) {
        Product product = new Product(productRequestDto.name(), productRequestDto.price(), productRequestDto.imageUrl());
        productRepository.save(product);
    }

    // repository를 호출해서 DB에 담긴 로우를 반환하는 함수
    public ProductResponseDto selectProduct(long productId) {
        Product product = productRepository.findById(productId).get();
        return new ProductResponseDto(product.getProductId(), product.getName(), product.getPrice(),
            product.getImage());
    }

    // 전체 제품 정보를 반환하는 함수
    public List<ProductResponseDto> selectProducts(@Valid PageRequestDto pageRequestDto) {
        int pageNumber = pageRequestDto.pageNumber();
        int pageSize = PageRequestDto.PAGE_SIZE;
        Sort sort = SortingStateUtility.getSort(pageRequestDto.sortingState());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> products = productRepository.findAll(pageable);
        return products.stream().map(product -> new ProductResponseDto(product.getProductId(),
            product.getName(), product.getPrice(), product.getImage())).collect(Collectors.toList());
    }

    // repository를 호출해서 특정 로우를 파라메터로 전달된 DTO로 교체하는 함수
    @Transactional
    public void updateProduct(long productId, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(productId).get();
        product.updateProduct(productRequestDto.name(), productRequestDto.price(),
            productRequestDto.imageUrl());
    }

    // repository를 호출해서 특정 로우를 제거하는 함수
    @Transactional
    public void deleteProduct(long productId) {
        verifyExistence(productId);
        productRepository.deleteById(productId);
    }
    
    // get()을 사용하지 않는 delete 작업에서 사용할 검증
    private void verifyExistence(long productId) {
        if (!productRepository.existsById(productId)) {
            throw new NoSuchElementException("이미 삭제된 제품입니다.");
        }
    }
}
