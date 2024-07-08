package gift.service;

import gift.DTO.ProductDTO;
import gift.domain.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    /*
     * DB에 저장된 모든 Product 객체를 불러와 전달해주는 로직
     */
    public List<ProductDTO> loadAllProduct(){
        List<ProductDTO> products = new ArrayList<>();

        List<Product> all = productRepository.findAll();
        for (Product product : all) {
            products.add(new ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl()
            ));
        }
        return products;
    }
    /*
     * DB에 저장된 모든 Product의 ID를 불러와 전달해주는 로직
     */
    public List<Long> loadAllId(){
        return productRepository.findAllId();
    }

    public ProductDTO loadOneProduct(Long id){
        Product byId = productRepository.findById(id);
        return new ProductDTO(
                byId.getId(),
                byId.getName(),
                byId.getPrice(),
                byId.getImageUrl()
        );
    }
    /*
     * 객체를 전달받아 DB에 저장해주는 로직
     */
    public void createProduct(ProductDTO product){
        Product productEntity = new Product(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
        productRepository.save(productEntity);
    }
    /*
     * DB에 있는 특정한 ID의 객체를 삭제해주는 로직
     */
    public void deleteProduct(Long id){
        productRepository.delete(id);
    }
    /*
     * 현재 DB에 존재하는 Product를 새로운 Product로 대체하는 로직
     */
    public void updateProduct(ProductDTO product, Long id){
        Product productEntity = new Product(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
        productRepository.update(productEntity, id);
    }
    /*
     * 새로운 ID가 기존 ID와 중복되었는지를 확인하는 로직
     */
    public boolean isDuplicate(Long id){
        List<Long> allId = loadAllId();
        return allId.contains(id);
    }

}
