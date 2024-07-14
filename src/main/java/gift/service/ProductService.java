package gift.service;

import gift.domain.Product;
import gift.dto.ProductUpdateRequestDTO;
import gift.exception.ProductErrorCode;
import gift.exception.ProductException;
import gift.repository.ProductJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    @Autowired
    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    public Product findById(long id) {
        return productJpaRepository.findById(id)
            .orElseThrow(() -> new ProductException(ProductErrorCode.ID_NOT_EXISTS));
    }

    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    public void deleteById(long id) {
        productJpaRepository.deleteById(id);
    }

    public Product updateProduct(ProductUpdateRequestDTO requestDTO) {
        long id = requestDTO.getId();
        Product product = productJpaRepository.findById(id)
            .orElseThrow(() -> new ProductException(ProductErrorCode.ID_NOT_EXISTS));
        product.setName(requestDTO.getName());
        product.setPrice(requestDTO.getPrice());
        product.setImageUrl(requestDTO.getImageUrl());
        return productJpaRepository.save(product);
    }

    public void deleteSelectedProducts(List<Long> ids) {
        for (Long id : ids) {
            if(!productJpaRepository.existsById(id)){
                throw new ProductException(ProductErrorCode.ID_NOT_EXISTS);
            }
            productJpaRepository.deleteById(id);
        }
    }

    public Page<Product> findPage(Pageable pageable) {
        return productJpaRepository.findAll(pageable);
    }
}
