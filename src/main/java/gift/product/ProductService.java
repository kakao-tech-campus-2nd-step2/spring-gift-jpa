package gift.product;

import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
            .map(ProductDTO::fromProduct)
            .toList();
    }

    public ProductDTO getProductById(long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        return ProductDTO.fromProduct(product);
    }

    public boolean existsByName(String name){
        return productRepository.existsByName(name);
    }

    public void addProduct(ProductDTO product) {
        if(productRepository.existsByName(product.getName())){
            throw new IllegalArgumentException("존재하는 이름입니다,");
        }
        productRepository.save(product.toProduct());
    }

    public void updateProduct(ProductDTO productDTO) throws NotFoundException {
        Product product = productRepository.findById(productDTO.getId()).orElseThrow(NotFoundException::new);
        if(productRepository.existsByName(product.getName())){
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
        product.update(productDTO.getPrice(),productDTO.getName(),productDTO.getImageUrl());
        productRepository.save(product);
    }

    public void deleteProduct(long id) throws NotFoundException {
        productRepository.findById(id).orElseThrow(NotFoundException::new);
        productRepository.deleteById(id);
    }
}
