package gift.repository.fixture;

import gift.domain.Product;

public class ProductFixture {
    public static Product createProduct(String name,int price,String imageUrl){
        return new Product(name,price,imageUrl);
    }
}
