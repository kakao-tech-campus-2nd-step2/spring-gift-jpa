package gift.service;

import gift.controller.dto.ProductDTO;
import gift.domain.Product;
import gift.repository.ProductRepository;
import gift.utils.error.NotpermitNameException;
import gift.utils.error.ProductAlreadyExistException;
import gift.utils.error.ProductNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GiftService {

    private final ProductRepository productRepository;

    public GiftService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).
            orElseThrow(() -> new ProductNotFoundException("Product NOT FOUND"));
    }

    public List<Product> getAllProduct() {
        List<Product> ALL = productRepository.findAll();
        if (ALL.isEmpty()) {
            throw new ProductNotFoundException("Product NOT FOUND");
        }
        return ALL;
    }

    public ProductDTO postProducts(ProductDTO productDTO) {
        validateProductName(productDTO.getName());

        // 먼저 동일한 ID의 제품이 이미 존재하는지 확인
        if (productDTO.getId() != null && productRepository.existsById(productDTO.getId())) {
            throw new ProductAlreadyExistException(
                "Product with ID " + productDTO.getId() + " already exists");
        }

        Product product = new Product(productDTO.getId(), productDTO.getName(),
            productDTO.getPrice(), productDTO.getImageUrl());

        Product savedProduct = productRepository.save(product);

        return productDTO;
    }

    public ProductDTO putProducts(ProductDTO productDTO, Long id) {
        validateProductName(productDTO.getName());

        Product productById = productRepository.findById(id).
            orElseThrow(() -> new ProductNotFoundException("Product NOT FOUND"));
        productById.setId(productDTO.getId());
        productById.setName(productDTO.getName());
        productById.setPrice(productDTO.getPrice());
        productById.setImageUrl(productDTO.getImageUrl());
        Product save = productRepository.save(productById);


        return new ProductDTO(save.getId(), save.getName(), save.getPrice(), save.getImageUrl());
    }

    public Long deleteProducts(Long id) {
        productRepository.findById(id).orElseThrow(
            ()-> new ProductNotFoundException("Product NOT FOUND"));
        productRepository.deleteById(id);
        return id;
    }

    private void validateProductName(String name) {
        if (name.replace(" ", "").contains("카카오")) {
            throw new NotpermitNameException("\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있다.");
        }
    }

}
