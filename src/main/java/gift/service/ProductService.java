package gift.service;

import gift.exception.NameException;
import gift.domain.Product;
import gift.dto.ProductDto;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product findById(Long id){
        return productRepository.findById(id);
    }
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }
    public void create(ProductDto productDto) {
        Product product = new Product(null, productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
        productRepository.save(product);
    }

    public void update(Long id, ProductDto productDto){
        Product product = productRepository.findById(id);
        if(product != null) {
            Product updateProduct = new Product(product.getId(), productDto.getName(),
                productDto.getPrice(), productDto.getImageUrl());
            productRepository.update(id, product);
        }

    }
    public void delete(Long id){
        productRepository.delete(id);
    }

}
