package gift.service;

import gift.dto.wish.WishCreateDTO;
import gift.dto.wish.WishInfoDTO;
import gift.dto.wish.WishRequestDTO;
import gift.dto.wish.WishResponseDTO;
import gift.exception.ForbiddenRequestException;
import gift.repository.UserDAO;
import gift.repository.WishDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishDAO wishDAO;
    private final UserDAO userDAO;

    public WishService(WishDAO wishDAO, UserDAO userDAO) {
        this.wishDAO = wishDAO;
        this.userDAO = userDAO;
    }

    public List<WishResponseDTO> getWishes(String email) {
        long userId = userDAO.findUserByEmail(email).id();

        return wishDAO.findWishes(userId).stream().map((wish) -> new WishResponseDTO(
                wish.id(),
                wish.productId(),
                wish.quantity()
        )).toList();
    }

    public WishResponseDTO addWish(String email, WishRequestDTO wishRequestDTO) {
        long userId = userDAO.findUserByEmail(email).id();

        WishInfoDTO wishInfoDTO = wishDAO.create(new WishCreateDTO(
                userId,
                wishRequestDTO.productId(),
                wishRequestDTO.quantity()
        ));

        return new WishResponseDTO(
                wishInfoDTO.id(),
                wishInfoDTO.productId(),
                wishInfoDTO.quantity()
        );
    }

    public void deleteWish(String email, long wishId) {
        long userId = userDAO.findUserByEmail(email).id();

        if (wishDAO.wishOwner(wishId) != userId) {
            throw new ForbiddenRequestException("user is not owner of wish");
        }

        wishDAO.delete(wishId);
    }
}
