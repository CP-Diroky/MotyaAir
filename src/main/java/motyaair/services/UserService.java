package motyaair.services;

import motyaair.dto.UserDto.UpdateUserDto;
import motyaair.models.User;

public interface UserService {

    User addUser(User user);

    User changeUser(Long userId, UpdateUserDto userData);

    void deleteUser(Long userId);
}
