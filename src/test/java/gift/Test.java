package gift;


import gift.controller.ProductController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class Test {
    @Autowired
    ProductController productController;
    @Autowired
    JdbcTemplate jdbcTemplate;

//    @org.junit.jupiter.api.Test
//    public void getTest() {
//        //ProductController productController = new ProductController();
//        productController.addProduct( 1,"1",1,"1","1");
//        productController.addProduct( 2,"2",2,"2","2");
//        System.out.println(productController.getProducts());
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addTest(){
//        //ProductController productController = new ProductController();
//        productController.addProduct(1,"1",1,"1","1");
//        System.out.println(productController.getProducts());
//    }
//
//    @org.junit.jupiter.api.Test
//    public void deleteTest() {
//        //ProductController productController = new ProductController();
//        productController.addProduct( 1,"1",1,"1","1");
//        productController.addProduct( 2,"2",2,"2","2");
//        productController.deleteProduct(1);
//        System.out.println(productController.getProducts());
//    }
//
//    @org.junit.jupiter.api.Test
//    public void modifyTest(){
//        //ProductController productController = new ProductController();
//        productController.addProduct( 1,"1",1,"1","1");
//        productController.addProduct( 2,"2",2,"2","2");
//        productController.modifyProduct(1,"modifyname",3,"3","3");
//        System.out.println(productController.getProducts());
//    }
}
