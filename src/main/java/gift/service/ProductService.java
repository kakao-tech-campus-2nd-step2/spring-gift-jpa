package gift.service;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //전체 조회
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    //하나 조회
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    //저장
    public void saveProduct(Product product) {
        validateProduct(product);
        productRepository.save(product);
    }

    //삭제
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, Product newProduct) {
        Optional<Product> getProduct = productRepository.findById(id);
        if (getProduct.isPresent()){
            Product oldProduct = getProduct.get();

            String name = oldProduct.getName();
            int price = oldProduct.getPrice();
            String imageUrl = oldProduct.getImageUrl();

            String newName = newProduct.getName();
            String newImageUrl = newProduct.getImageUrl();

            if (newName != null && !newName.isEmpty()) {
                validateProduct(newProduct);
                name = newName;
            }
            if (newProduct.getPrice() != null) {
                price = newProduct.getPrice();
            }

            if (newImageUrl != null && !newImageUrl.isEmpty()) {
                imageUrl = newImageUrl;
            }

            Product updatedProduct = new Product(oldProduct.getId(), name, price, imageUrl);
            productRepository.save(updatedProduct);
        }
    }

    private void validateProduct(Product product) {
        if (!isCorrectName(product.getName())) {
            throw new IllegalArgumentException("이름은 최대 15자 이내이어야 하며, 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");
        }
        if (isContainsKakao(product.getName())) {
            throw new IllegalArgumentException("\"카카오\"는 MD와 협의 후에 사용 가능합니다.");
        }
    }

    public boolean isCorrectName(String name){
        if(name.length()>15){
            return false;
        }
        String letters = "()[]+-&/_ ";
        for(int i=0; i<name.length(); i++){
            char one = name.charAt(i);
            if(!Character.isLetterOrDigit(one) && !letters.contains(Character.toString(one))){
                return false;
            }
        }
        return true;
    }

    public boolean isContainsKakao(String name){
        return name.contains("카카오");
    }
}