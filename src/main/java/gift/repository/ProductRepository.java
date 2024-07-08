package gift.repository;

import gift.domain.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long addProduct(Product product) {
        String sql = "insert into products values(?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql,product.getId(), product.getName(),product.getPrice(),product.getImageUrl());
        if(result ==1){
            return product.getId();
        }
        return -1L;
    }

    public Long deleteProduct(Long id){
        String sql="delete from products where id=?";
        int result = jdbcTemplate.update(sql,id);
        if(result==1){
            return id;
        }
        return -1L;
    }

    public Long updateProduct(Product product) {
        String sql = "update products set id=?, name = ?, price = ?, imageUrl = ? where id = ?";
        int result=jdbcTemplate.update(sql,product.getId(),product.getName(),product.getPrice(),product.getImageUrl(),product.getId());
        if(result==1){
            return product.getId();
        }
        return -1L;
    }

    public List<Product> findAll() {
        String sql = "select * from products";
        return jdbcTemplate.query(sql, productRowMapper());
    }
    private RowMapper<Product> productRowMapper(){
        return ((((rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            product.setImageUrl(rs.getString("imageUrl"));
            return product;
        })));
    }
    public Product findById(Long id) {
        String sql = "select * from products where id=?";
        List<Product> products = jdbcTemplate.query(sql, productRowMapper(), id);
        if (products.isEmpty()) {
            return null;
        }
        return products.get(0);
    }



}