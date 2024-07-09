package gift.controller;

import gift.dto.UserDto;
import gift.service.UserListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiUserListController {
    UserListService userListService;

    public ApiUserListController(UserListService userListService) {
        this.userListService = userListService;
    }

    @GetMapping("/user/list")
    public List<UserDto> UserList() {
        return userListService.getAll();
    }
}
