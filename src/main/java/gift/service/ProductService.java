package gift.service;

import gift.dto.ProductDTO;
import gift.entity.Product;
import gift.exception.ProductException;
import gift.repository.ProductRepository;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product getProduct(long productId) {
        Optional<Product> product = repository.findById(productId);
        return product.orElseThrow(() -> new ProductException("상품을 찾을 수 없습니다."));
    }

    public List<Product> getAllProducts() {
        List<Product> products = repository.findAll();
        //if (products.isEmpty()) throw new ProductException("상품이 존재하지 않습니다.");
        return products;
    }

    public void saveProduct(ProductDTO productDTO){
        try {
            Product product = toEntity(productDTO);
            repository.save(product);
        } catch (DataAccessException e) {
            throw new ProductException("데이터베이스 접근 오류가 발생했습니다.");
        } catch (PersistenceException e) {
            throw new ProductException("영속성 오류가 발생했습니다.");
        } catch (ConstraintViolationException e) {
            throw new ProductException("제약 조건 위반 오류가 발생했습니다.");
        } catch (Exception e) {
            throw new ProductException("알 수 없는 오류가 발생했습니다.");
        }
    }

    public void deleteProduct(Long productId) {
        Optional<Product> existingProduct = repository.findById(productId);
        existingProduct.orElseThrow(() -> new ProductException("상품을 찾을 수 없어서 삭제할 수 없습니다."));
        repository.deleteById(productId);
    }

    @Transactional
    public void updateProduct(Long productId, ProductDTO productDTO) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductException("상품을 찾을 수 없어서 업데이트 할 수 없습니다."));
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setImageUrl(productDTO.imageUrl());
        repository.save(product);
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setImageUrl(dto.imageUrl());
        return product;
    }


}
