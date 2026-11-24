package motyaair.controllers.user_level;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import motyaair.dto.UserDto.UpdateUserDto;
import motyaair.dto.UserDto.UserDto;
import motyaair.mappers.RowMappers;
import motyaair.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto addUser(@RequestBody @Valid UserDto userDto) {
        return RowMappers.userDto(userService.addUser(RowMappers.user(userDto)));
    }

    @PatchMapping("/{userId}")
    public UserDto changeUser(@PathVariable @Positive Long userId, @RequestBody @Valid UpdateUserDto userData) {
        return RowMappers.userDto(userService.changeUser(userId, userData));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive Long userId) {
        userService.deleteUser(userId);
    }
}
