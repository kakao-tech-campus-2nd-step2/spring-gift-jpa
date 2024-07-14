package gift.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gift.exception.InvalidProductException;
import gift.exception.InvalidUserException;
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
	
	public Page<Wishlist> getWishlist(String token, BindingResult bindingResult, Pageable pageable) {
        User user = getUserFormToekn(token, bindingResult);
        return wishlistRepository.findByUserId(user.getId(), pageable);
    }
	
	public void addWishlist(String token, Wishlist wishlist, BindingResult bindingResult) {
		validateBindingResult(bindingResult);
		User user = getUserFormToekn(token, bindingResult);
        Product product = findProductById(wishlist.getProduct().getId());
        Wishlist newWishlist = new Wishlist(user, product);
        wishlistRepository.save(newWishlist);
	}
	
	public void removeWishlist(String token, Wishlist wishlist, BindingResult bindingResult) {
		validateBindingResult(bindingResult);
		User user = getUserFormToekn(token, bindingResult);
        Wishlist deleteWishlist = findWishlistById(wishlist.getId());
        validateUserPermission(deleteWishlist, user);
        wishlistRepository.delete(deleteWishlist);
	}
	
	public void updateWishlistQuantity(String token, Wishlist wishlist, BindingResult bindingResult) {
		validateBindingResult(bindingResult);
		User user = getUserFormToekn(token, bindingResult);
        Wishlist updateWishlist = findWishlistById(wishlist.getId());
        validateUserPermission(updateWishlist, user);
        if(wishlist.getQuantity() == 0) {
        	wishlistRepository.delete(updateWishlist);
        	return;
        }
        updateWishlist.setQuantity(wishlist.getQuantity());
        wishlistRepository.save(updateWishlist);
	}
	
	private void validateBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        	String errorMessage = bindingResult
					.getFieldError()
					.getDefaultMessage();
			throw new InvalidUserException(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
	
	private User getUserFormToekn(String token, BindingResult bindingResult) {
		String email = authService.parseToken(token);
		return authService.searchUser(email, bindingResult);
	}
	
	private void validateUserPermission(Wishlist wishlist, User user) {
		if(!wishlist.getUser().equals(user)) {
			throw new UnauthorizedException("You do not have permission to perform this action on the wishlist item.");
		}
	}
	
	private Product findProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new InvalidProductException("The product does not exits."));
	}
	
	private Wishlist findWishlistById(Long id) {
		return wishlistRepository.findById(id)
				.orElseThrow(() -> new InvalidProductException("Wishlist item not found."));
	}
}
