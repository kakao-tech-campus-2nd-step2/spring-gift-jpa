package gift.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import gift.dto.ProductDto;
import gift.entity.Product;
import gift.exception.CustomException;
import gift.repository.ProductRepository;

@Service
public class ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
        .map(ProductDto::fromEntity)
        .collect(Collectors.toList());
    }


    public ProductDto findById(Long id){
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new CustomException("Product with id " + id + " not found", HttpStatus.NOT_FOUND));
        return product.toDto(product);
    }

    public void addProduct(ProductDto productDto) {

        if(productRepository.findById(productDto.getId()).isEmpty()){
            Product product = productDto.toEntity();
            productRepository.save(product);
        }else{
            throw new CustomException("Product with id " + productDto.getId() + "exists", HttpStatus.CONFLICT);
        }
    }

    public void updateProduct(ProductDto productDto) {

        Optional<Product> optionalProduct = productRepository.findById(productDto.getId());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setImageUrl(productDto.getImageUrl());
            productRepository.save(product);
        }else{
            throw new CustomException("Product with id " + productDto.getId() + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}