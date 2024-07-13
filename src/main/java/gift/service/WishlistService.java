package gift.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gift.exception.InvalidProductException;
import gift.exception.UnauthorizedException;
import gift.model.Product;
import gift.model.User;
import gift.model.Wishlist;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;

@Service
public class WishlistService {
	
	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private AuthService authService;
	
	public List<Wishlist> getWishlist(String token, BindingResult bindingResult) {
        String email = authService.parseToken(token);
        User user = authService.searchUser(email, bindingResult);
        return wishlistRepository.findByUserId(user.getId());
    }
	
	public void addWishlist(String token, Wishlist wishlist, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new InvalidProductException(bindingResult.getFieldError().getDefaultMessage());
		}
		
		String email = authService.parseToken(token);
        User user = authService.searchUser(email, bindingResult);
        
        Product product = productRepository.findById(wishlist.getProduct().getId())
        		.orElseThrow(() -> new InvalidProductException("The product does not exits."));
        
        Wishlist newWishlist = new Wishlist(user, product);
        wishlistRepository.save(newWishlist);
	}
	
	public void removeWishlist(String token, Wishlist wishlist, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new InvalidProductException(bindingResult.getFieldError().getDefaultMessage());
		}
		String email = authService.parseToken(token);
        User user = authService.searchUser(email, bindingResult);
		
        Wishlist deleteWishlist = wishlistRepository.findById(wishlist.getId())
        		.orElseThrow(() -> new InvalidProductException("Product colud not be removed from wishlist."));
        if(!deleteWishlist.getUser().equals(user)) {
        	throw new UnauthorizedException("You do not have permission to remove this wishlist item.");
        }
        wishlistRepository.delete(deleteWishlist);
	}
	
	public void updateWishlistQuantity(String token, Wishlist wishlist, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new InvalidProductException(bindingResult.getFieldError().getDefaultMessage());
		}
		String email = authService.parseToken(token);
        User user = authService.searchUser(email, bindingResult);
		
        Wishlist updateWishlist = wishlistRepository.findById(wishlist.getId())
        		.orElseThrow(() -> new InvalidProductException("Product could not be update in wishlist."));
        if(!updateWishlist.getUser().equals(user)) {
        	throw new UnauthorizedException("You do not have permission to remove this wishlist item.");
        }
        
        if(wishlist.getQuantity() == 0) {
        	wishlistRepository.delete(updateWishlist);
        	return;
        }
        updateWishlist.setQuantity(wishlist.getQuantity());
        wishlistRepository.save(updateWishlist);
	}
}
