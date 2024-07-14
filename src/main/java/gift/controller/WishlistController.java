package gift.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gift.model.Wishlist;
import gift.service.WishlistService;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
	
	@Autowired
	private WishlistService wishlistService;
	
	@GetMapping
	public ResponseEntity<Page<Wishlist>> getWishlist(@RequestHeader("Authorization") String token, BindingResult bindingResult,
			@PageableDefault(sort="name") Pageable pageable){
		Page<Wishlist> wishlist = wishlistService.getWishlist(token, bindingResult, pageable);
		if(wishlist.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(wishlist);
	}

	@PostMapping
	public ResponseEntity<String> addWishlist(@RequestHeader("Authorization") String token, @RequestBody Wishlist wishlist
			, BindingResult bindingResult){
		wishlistService.addWishlist(token, wishlist, bindingResult);
		return ResponseEntity.ok("Product added to wishlist successfully.");
	}
	
	@DeleteMapping
	public ResponseEntity<String> removeWishlist(@RequestHeader("Authorization") String token, @RequestBody Wishlist wishlist
			, BindingResult bindingResult){
		wishlistService.removeWishlist(token, wishlist, bindingResult);
		return ResponseEntity.ok("Product removed from wishlist successfully.");
	}
	
	@PutMapping
	public ResponseEntity<String> updateWishlist(@RequestHeader("Authorization") String token, @RequestBody  Wishlist wishlist
			, BindingResult bindingResult){
		wishlistService.updateWishlistQuantity(token, wishlist, bindingResult);
		return ResponseEntity.ok("Product quantity updated successfully.");
	}
}
