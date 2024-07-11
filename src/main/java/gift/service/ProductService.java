package gift.service;

import gift.DTO.ProductDTO;
import gift.domain.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * DB에 저장된 Product를 ID를 기준으로 찾아 반환
     */
    public ProductDTO loadOneProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(NoSuchFieldError::new);
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }
    /*
     * 객체를 전달받아 DB에 저장
     */
    public void createProduct(ProductDTO product){
        Product productEntity = new Product(
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
        productRepository.deleteById(id);
    }
    /*
     * 현재 DB에 존재하는 Product를 새로운 Product로 대체하는 로직
     */
    public void updateProduct(ProductDTO product, Long id){
        Optional<Product> byId = productRepository.findById(id);
        if(byId.isEmpty()){
            throw new NullPointerException("해당 id를 가진 객체는 존재하지 않습니다");
        }
        Product product1 = byId.get();

        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setImageUrl(product.getImageUrl());

        productRepository.save(product1);
    }
    /*
     * 새로운 ID가 기존 ID와 중복되었는지를 확인하는 로직
     */
    public boolean isDuplicate(Long id){
        return productRepository.existsById(id);
    }

}
