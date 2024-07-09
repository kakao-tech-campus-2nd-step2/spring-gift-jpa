package gift.service;

import gift.domain.Product;
import gift.dto.ProductRequest;
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

    public Product find(Long id){
        return productRepository.findById(id);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product createProduct(ProductRequest productRequest) {
        return productRepository.save(productRequest);
    }

    public Product updateProduct(Long id, ProductRequest productRequest){
        Product product = productRepository.findById(id);

        if(product != null) {
            Product updateProduct = new Product(product.getId(), productRequest.getName(),
                productRequest.getPrice(), productRequest.getImageUrl());

            return productRepository.update(product);
        }

        return null;
    }
    public void deleteProduct(Long id){
        productRepository.delete(id);
    }

}
