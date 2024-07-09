package gift.service;

import gift.constants.Messages;
import gift.domain.Product;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponseDto save(ProductRequestDto productDto){
        Long id = productRepository.save(productDto.toEntity());
        return new ProductResponseDto(id,productDto.getName(),productDto.getPrice(),productDto.getImageUrl());
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id){
         Product product = findProductByIdOrThrow(id);
         return ProductResponseDto.from(product);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findByName(String name){
        Product product =  productRepository.findByName(name)
                .orElseThrow(()->  new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_NAME));
        return ProductResponseDto.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findByIds(List<Long> ids){
        return productRepository.findByIds(ids)
                .orElseThrow(()-> new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_ID))
                .stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::from)
                .toList();
    }

    @Transactional
    public Long deleteById(Long id){
        findProductByIdOrThrow(id);
        return productRepository.delete(id);
    }

    @Transactional
    public ProductResponseDto updateById(Long id, ProductRequestDto productDto){
        findProductByIdOrThrow(id);
        Product editProduct = productRepository.update(id, productDto.toEntity());
        ProductResponseDto productResponseDto = ProductResponseDto.from(editProduct);
        productResponseDto.setId(id);
        return productResponseDto;
    }

    private Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_ID));
    }
}
