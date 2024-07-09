package gift.service;

import gift.dto.ProductDTO;
import gift.entity.Product;
import gift.exception.BadRequestExceptions.BadRequestException;
import gift.exception.BadRequestExceptions.NoSuchProductIdException;
import gift.exception.InternalServerExceptions.InternalServerException;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ParameterValidator parameterValidator;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ParameterValidator parameterValidator,
            ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.parameterValidator = parameterValidator;
    }

    public void addProduct(ProductDTO productDTO) throws RuntimeException {
        try {
            productRepository.save(new Product(productDTO));
        } catch (Exception e) {
            if(e instanceof IllegalArgumentException || e instanceof DataIntegrityViolationException)
                throw new BadRequestException("잘못된 값을 입력했습니다.");

            throw new InternalServerException(e.getMessage());
        }

    }

    public List<ProductDTO> getProductList() {
        return ProductConverter.convertToProductDTO(productRepository.findAll());
    }

    public void updateProduct(Long id, ProductDTO productDTO) throws RuntimeException {
        parameterValidator.validateParameter(id, productDTO);
        Optional<Product> productInDb = productRepository.findById(id);

        if(!productInDb.isPresent())
            throw new NoSuchProductIdException("id가 %d인 상품은 존재하지 않습니다.".formatted(id));

        Product productInDB = productInDb.get();
        Product product = new Product(productDTO); //이따가 검증하는거 해야함
        productInDB.changeProduct(product);
    }

    public void deleteProduct(Long id) throws RuntimeException {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            if(e instanceof EmptyResultDataAccessException)
                throw new NoSuchProductIdException("id가 %d인 상품은 존재하지 않습니다.".formatted(id));

            throw new InternalServerException(e.getMessage());
        }
    }
}
