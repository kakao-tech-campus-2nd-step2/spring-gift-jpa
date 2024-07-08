package gift.repository;

import gift.dto.ProductDTO;
import gift.entity.Option;
import gift.entity.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Repository
@Validated
public class ProductRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ProductDTO> getAllProduct(){
        var sql = "select * from PRODUCT inner join option on product.id = option.id";
        List<ProductDTO> products = jdbcTemplate.query(sql,(rs, rowNum) -> new ProductDTO(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imageUrl"),
                rs.getString("option")
        ));
        return products;
    }

    public void saveProduct(@Valid Product product) {
        var sql = "insert into product(id,name,price,imageUrl) values (?,?,?,?)";
        jdbcTemplate.update(sql, product.getId(),product.getName(),product.getPrice(),product.getImageUrl());
    }

    public void saveOption(@Valid Option option) {
        var sql = "insert into option(id,option) values(?,?)";
        jdbcTemplate.update(sql, option.getId(), option.getOption());
    }

    public void deleteProductByID(int id) {
        var sql = "delete from product where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void deleteOptionsByID(int id) {
        var sql ="delete from option where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Product findProductByID(int id) {
        var sql = "select * from PRODUCT inner join option on product.id = option.id where product.id = ?";
        List<Product> products = jdbcTemplate.query(sql,new Object[]{id}, (rs, rowNum) -> new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imageUrl")

        ));
        return products.getFirst();
    }
    public List<Option> findOptionByID(int id){
        var sql = "select * from option where id = ?";
        List<Option> options = jdbcTemplate.query(sql,new Object[]{id}, (rs, rowNum) -> new Option(
                rs.getInt("id"),
                rs.getString("option")
        ));
        return options;
    }
    public boolean isExistProduct(@Valid Product  product){
        var sql = "select * from product where id=?";
        List<Product> products = jdbcTemplate.query(sql,new Object[]{product.getId()}, (rs, rowNum) -> new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imageUrl")
        ));
        return !products.isEmpty();
    }

    public boolean isExistOption(@Valid Option saveOptionDTO){
        var sql = "select * from option where id=? and option=?";
        List<Option> opt = jdbcTemplate.query(sql,new Object[]{saveOptionDTO.getId(),saveOptionDTO.getOption()}, (rs, rowNum) -> new Option(
                rs.getInt("id"),
                rs.getString("option")
        ));
        return !opt.isEmpty();
    }
}
