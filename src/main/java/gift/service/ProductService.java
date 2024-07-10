package gift.service;

import gift.dao.ProductDao;
import gift.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.selectAllProduct();
    }

    public Product getProductById(Long id) {
        return productDao.selectProduct(id);
    }

    public void postProduct(Product product) {
        validateProduct(product);
        productDao.insertProduct(product);
    }

    public void deleteProduct(Long id) {
        productDao.deleteProduct(id);
    }

    public void updateProduct(Long id, Product newProduct) {
        Product oldProduct = productDao.selectProduct(id);
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

        productDao.updateProduct(updatedProduct);
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