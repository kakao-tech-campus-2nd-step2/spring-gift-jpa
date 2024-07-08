package gift.controller;

import static gift.util.ResponseEntityUtil.responseError;

import com.fasterxml.jackson.core.JsonProcessingException;
import gift.annotation.LoginMember;
import gift.constants.ResponseMsgConstants;
import gift.dto.ProductDTO;
import gift.dto.ResponseDTO;
import gift.dto.UserDTO;
import gift.dto.WishListDTO;
import gift.service.WishListService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/products/wishes")
public class WishListController {

    private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("")
    public String getWishes(Model model, @LoginMember UserDTO userDTO) {
        try {
            WishListDTO wishListDTO = wishListService.getWishList(userDTO);
            model.addAttribute("wishList", wishListDTO);
        } catch (RuntimeException e) {
            responseError(e);
        }
        return "getWishes";
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO> addWishes(@RequestBody @Valid ProductDTO productDTO,
            @LoginMember UserDTO userDTO) {
        try {
            wishListService.addWishListProduct(userDTO, productDTO);
        } catch (RuntimeException e) {
            responseError(e);
        }
        return new ResponseEntity<>(new ResponseDTO(false, ResponseMsgConstants.WELL_DONE_MESSAGE),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteWishes(@PathVariable @Min(1) @NotNull Integer id,
            @LoginMember UserDTO userDTO) {
        try {
            wishListService.removeWishListProduct(userDTO, id);
        } catch (RuntimeException e) {
            responseError(e);
        } catch (JsonProcessingException e) {
            responseError(e);
        }
        return new ResponseEntity<>(new ResponseDTO(false, ResponseMsgConstants.WELL_DONE_MESSAGE),
                HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{quantity}")
    public ResponseEntity<ResponseDTO> setWishes(@PathVariable @Min(0) @NotNull Integer quantity, @LoginMember UserDTO userDTO,
            @RequestBody @Valid ProductDTO productDTO) {
        try {
            wishListService.setWishListNumber(userDTO, productDTO, quantity);
        } catch (RuntimeException e) {
            responseError(e);
        }
        return new ResponseEntity<>(new ResponseDTO(false, ResponseMsgConstants.WELL_DONE_MESSAGE),
                HttpStatus.OK);
    }
}
