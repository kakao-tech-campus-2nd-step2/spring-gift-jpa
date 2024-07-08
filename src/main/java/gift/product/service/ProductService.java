package gift.product.service;

import gift.product.dto.LoginMember;
import gift.product.dto.ProductDto;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public static final String NAME_KAKAO = "카카오";
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductAll(LoginMember loginMember) {
        return productRepository.findAll(loginMember);
    }

    public Product getProduct(Long id, LoginMember loginMember) {
        return getValidatedProduct(id, loginMember);
    }

    public Product insertProduct(ProductDto productDto, LoginMember loginMember) {
        Product product = new Product(productDto.name(), productDto.price(), productDto.imageUrl());
        product = productRepository.save(product, loginMember);

        return product;
    }

    public Product updateProduct(Long id, ProductDto productDTO, LoginMember loginMember) {
        getValidatedProduct(id, loginMember);

        Product product = new Product(id, productDTO.name(), productDTO.price(),
            productDTO.imageUrl());
        productRepository.update(product, loginMember);
        return product;
    }

    public void deleteProduct(Long id, LoginMember loginMember) {
        getValidatedProduct(id, loginMember);
        productRepository.delete(id, loginMember);
    }

    private Product getValidatedProduct(Long id, LoginMember loginMember) {
        try {
            return productRepository.findById(id, loginMember);
        } catch (DataAccessException e) {
            throw new NoSuchElementException("해당 ID의 상품이 존재하지 않습니다.");
        }
    }
}
