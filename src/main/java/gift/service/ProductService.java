package gift.service;

import gift.domain.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService (ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Transactional
    public Product save (Product product){
        return productRepository.save(product);
    }

    @Transactional(readOnly=true)
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) throws ProductNotFoundException{
        return productRepository.findById(id).get();
    }

    @Transactional
    public void updateProduct(Long id,Product product) throws ProductNotFoundException{
        productRepository.save(new Product(id,product.getName(),product.getPrice(),product.getImageUrl()));
    }

    public void deleteById(Long id) throws ProductNotFoundException{
        productRepository.deleteById(id);
    }
}
