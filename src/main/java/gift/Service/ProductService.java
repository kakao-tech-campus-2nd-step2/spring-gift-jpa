package gift.Service;

import gift.Exception.AuthorizedException;
import gift.Model.Product;
import gift.Model.ProductDAO;
import gift.Model.Role;
import gift.Model.UserInfoDAO;
import gift.Token.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductDAO productDAO;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserInfoDAO userInfoDAO;

    public ProductService(ProductDAO productDAO, JwtTokenProvider jwtTokenProvider, UserInfoDAO userInfoDAO){
        this.productDAO = productDAO;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userInfoDAO = userInfoDAO;
        productDAO.insertProduct(new Product(1,"a",1, "b"));
    }

    public void add(String token, Product product){
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);
        if(userInfoDAO.selectUser(email) == null || !role.equals(Role.ADMIN.name())){
            throw new AuthorizedException();
        }
        productDAO.insertProduct(product);
    }

    public void delete(String token, Long id){
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);
        if(userInfoDAO.selectUser(email) == null || !role.equals(Role.ADMIN.name())){
            throw new AuthorizedException();
        }
        productDAO.deleteProduct(id);
    }

    public void edit(String token, Long id, Product product){
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);
        if(userInfoDAO.selectUser(email) == null || !role.equals(Role.ADMIN.name())){
            throw new AuthorizedException();
        }
        productDAO.updateProduct(id, product);
    }

    public List<Product> getAll(String token){
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);
        if(userInfoDAO.selectUser(email) == null || !role.equals(Role.ADMIN.name())){
            throw new AuthorizedException();
        }
        return productDAO.selectAllProduct();
    }

    public Product getById(String token, Long id){
        String email = jwtTokenProvider.getEmailFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);
        if(userInfoDAO.selectUser(email) == null || !role.equals(Role.ADMIN.name())){
            throw new AuthorizedException();
        }
        return productDAO.selectProduct(id);
    }


}
