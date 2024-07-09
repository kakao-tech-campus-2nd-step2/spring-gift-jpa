package gift.service;

import gift.dto.UserDTO;
import gift.entity.User;

public class UserConverter {
    public static UserDTO  convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO(user.getEmail(), user.getPassword(), user.getName(),
                user.getRole());

        return userDTO;
    }
}
