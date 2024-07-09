package gift.product;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public HttpStatus createProduct(Product newProduct) throws IllegalArgumentException{
        if(!newProduct.getName().matches("^((?!카카오).)*$")){
            throw new IllegalArgumentException("\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        }
        productRepository.save(newProduct);
        return HttpStatus.CREATED;
    }

    public HttpStatus updateProduct(Product changeProduct) {
        productRepository.update(changeProduct);

        return HttpStatus.OK;
    }

    public HttpStatus deleteProduct(Long id) {
        productRepository.deleteById(id);

        return HttpStatus.OK;
    }
}
