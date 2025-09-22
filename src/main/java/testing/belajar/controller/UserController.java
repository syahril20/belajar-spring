package testing.belajar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testing.belajar.dto.CreateUserRequest;
import testing.belajar.exception.BadRequestException;
import testing.belajar.services.UserService;
import testing.belajar.utils.ApiResponse;

import static testing.belajar.utils.GeneralConstant.SUCCESS;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse getUsers() {
        return new ApiResponse(200, SUCCESS, userService.getListUsers());
    }

    @PostMapping
    public ApiResponse createUser(@RequestBody CreateUserRequest request) throws BadRequestException {
        return new ApiResponse(200, SUCCESS, userService.saveUser(request));
    }

}
