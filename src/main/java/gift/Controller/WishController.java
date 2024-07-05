package gift.Controller;

import gift.DTO.ProductDto;
import gift.DTO.UserDto;
import gift.LoginUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
public class WishController {
  @PostMapping
  public void create(@RequestBody ProductDto productDto, @LoginUser UserDto user) {

  }
}
