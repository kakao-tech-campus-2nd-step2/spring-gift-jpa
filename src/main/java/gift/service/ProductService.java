package gift.service;

import gift.entity.Product;
import gift.repository.JdbcProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final JdbcProductRepository repository;

    @Autowired
    public ProductService(JdbcProductRepository repository) {
        this.repository = repository;
    }

    public Optional<Product> getProduct(long productId) {
        return repository.findById(productId);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public ResponseEntity<Product> selectProductById(long id){
        Optional<Product> product = getProduct(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<Collection<Product>> selectAllProducts() {
        return new ResponseEntity<>(getAllProducts(), HttpStatus.OK);
    }

    public ResponseEntity<String> saveProduct(Product product) {
        repository.save(product);
        return new ResponseEntity<>("저장 완료", HttpStatus.OK);
    }


    public ResponseEntity<String> deleteProduct(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    public ResponseEntity<String> updateProduct(long id, Product product) {
        repository.updateById(id, product);
        return new ResponseEntity<>("update 완료", HttpStatus.OK);
    }



}
