package gift.Service;

import gift.Exception.AuthorizedException;
import gift.Model.*;
import gift.Token.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistDAO wishlistDAO;
    private final ProductDAO productDAO;
    private final UserInfoDAO userInfoDAO;
    private final JwtTokenProvider jwtTokenProvider;

    public WishlistService(WishlistDAO wishlistDAO, ProductDAO productDAO, UserInfoDAO userInfoDAO, JwtTokenProvider jwtTokenProvider){
        this.wishlistDAO = wishlistDAO;
        this.productDAO = productDAO;
        this.userInfoDAO = userInfoDAO;
        this.jwtTokenProvider = jwtTokenProvider;
        // 테스트용 코드
        wishlistDAO.insertWishlist(new Wishlist("a",1, 1),"admin");
        wishlistDAO.insertWishlist(new Wishlist("b",2, 1),"admin");
        wishlistDAO.insertWishlist(new Wishlist("c",3, 1),"admin");
    }

    public void add(String token, Product product){
        try{
            Product product1 = productDAO.selectProductByName(product.name());
            String email = jwtTokenProvider.getEmailFromToken(token);
            String role = jwtTokenProvider.getRoleFromToken(token);
            if(userInfoDAO.selectUser(email) != null && (role.equals(Role.CONSUMER.name()) || role.equals(Role.ADMIN.name()))) {
                wishlistDAO.insertWishlist(new Wishlist(product1.name(),product1.price(), 1), email);
            }
        }
        catch(Exception e) {
            throw new AuthorizedException();
        }
    }

    public void delete(String token, String name){
        try{
            String email = jwtTokenProvider.getEmailFromToken(token);
            String role = jwtTokenProvider.getRoleFromToken(token);

            if(userInfoDAO.selectUser(email) != null && (role.equals(Role.CONSUMER.name()) || role.equals(Role.ADMIN.name()))){
                wishlistDAO.deleteWishlist(email, name);
            }
        }
        catch(Exception e) {
            throw new AuthorizedException();
        }
    }

    public List<Wishlist> viewAll(String token){
        try{
            String email = jwtTokenProvider.getEmailFromToken(token);
            String role = jwtTokenProvider.getRoleFromToken(token);
            System.out.println(role);

            if(userInfoDAO.selectUser(email) != null && (role.equals(Role.CONSUMER.name()) || role.equals(Role.ADMIN.name()))){
                return wishlistDAO.selectAllWishlist(email);
            }
        }
        catch(Exception e) {
            throw new AuthorizedException();
        }

        return null;
    }
}
