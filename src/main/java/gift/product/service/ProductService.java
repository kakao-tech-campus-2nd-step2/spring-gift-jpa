package gift.product.service;

import gift.product.dto.ProductDto;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(ProductDto productDto) {
        Product product = new Product(productDto.name(), productDto.price(), productDto.imgUrl());
        productRepository.save(product);
    }

    /** 기존의 findAll()은 모든 상품을 조회하지만
        페이지네이션을 추가한 findAll()은 주어진 페이지와 크기 및 정렬 기준에 따라 제한된 데이터만 가져옴. **/
    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(product -> new ProductDto(
                        product.product_id(),
                        product.name(),
                        product.getPrice(),
                        product.getImgUrl()));
    }

    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductDto(
                        product.product_id(),
                        product.name(),
                        product.getPrice(),
                        product.getImgUrl()))
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
    }

    public void update(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        product.update(productDto.name(), productDto.price(), productDto.imgUrl());
        productRepository.save(product);
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }
}