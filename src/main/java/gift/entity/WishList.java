package gift.entity;

import gift.dto.ProductDTO;
import gift.dto.WishListDTO;
import java.util.ArrayList;
import java.util.List;

public class WishList {
    private List<Product> products = new ArrayList<>();

    public WishList() {}

    public WishList(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        if (!products.contains(product)) {
            product.setQuantity(1);
            products.add(product);
            return;
        }
        int index = products.indexOf(product);
        Product productInList = products.get(index);
        productInList.setQuantity(productInList.getQuantity() + 1);
    }

    public boolean setNumbers(Product product, Integer value) {
        if (value < 1) return removeProduct(product);
        if (!products.contains(product)) return false;

        int index = products.indexOf(product);
        Product productInList = products.get(index);
        productInList.setQuantity(value);
        return true;
    }

    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    public boolean removeProduct(Integer id) {
        for (Product product : products) {
            if (product.getId() == id.intValue()){
                products.remove(product);
                return true;
            }
        }
        return false;
    }

    public List<Product> getProducts() {
        return products;
    }

    public WishListDTO convertToDTO() {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            productDTOs.add(product.convertToProductDTO(product));
        }
        return new WishListDTO(productDTOs);
    }
}
