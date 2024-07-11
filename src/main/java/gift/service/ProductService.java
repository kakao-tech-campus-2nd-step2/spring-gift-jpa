package gift.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import gift.dto.ProductDto;
import gift.entity.Product;
import gift.entity.WishList;
import gift.exception.CustomException;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService{

    private ProductRepository productRepository;
    private WishListRepository wishListRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public List<ProductDto> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
        .map(ProductDto::fromEntity)
        .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto findById(Long id){
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new CustomException("Product with id " + id + " not found", HttpStatus.NOT_FOUND));
        return product.toDto();
    }

    @Transactional
    public void addProduct(ProductDto productDto) {

        if(productRepository.findById(productDto.getId()).isEmpty()){
            Product product = productDto.toEntity();
            productRepository.save(product);
        }else{
            throw new CustomException("Product with id " + productDto.getId() + "exists", HttpStatus.CONFLICT);
        }
    }

    @Transactional
    public void updateProduct(ProductDto productDto) {

        Optional<Product> optionalProduct = productRepository.findById(productDto.getId());

        if (optionalProduct.isPresent()) {
            Product product = productDto.toEntity();
            productRepository.delete(optionalProduct.get());
            productRepository.save(product);
        }else{
            throw new CustomException("Product with id " + productDto.getId() + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void deleteProduct(Long id) {

        productRepository.findById(id)
            .orElseThrow(() -> new CustomException("Product with id " + id + " not found", HttpStatus.NOT_FOUND));

        List<WishList> wishList = wishListRepository.findByProductId(id);
        wishListRepository.deleteAll(wishList);

        productRepository.deleteById(id);
    }
}