package gift.service;

import gift.constants.Messages;
import gift.domain.Product;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.dto.WishResponseDto;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Transactional
    public void save(ProductRequestDto productDto){
        productRepository.save(productDto.toEntity());
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id){
         Product product = findProductByIdOrThrow(id);
         return ProductResponseDto.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getPagedProducts(Pageable pageable){
        return productRepository.findAll(pageable)
                .stream()
                .map(ProductResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findByName(String name){
        Product product =  productRepository.findByName(name)
                .orElseThrow(()->  new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_NAME));
        return ProductResponseDto.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::from)
                .toList();
    }

    @Transactional
    public void deleteById(Long id){
        findProductByIdOrThrow(id);
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Long id, ProductRequestDto productDto){
        findProductByIdOrThrow(id);
        productRepository.save(new Product(id, productDto.getName(), productDto.getPrice(), productDto.getImageUrl()));
    }

    private Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_ID));
    }
}
