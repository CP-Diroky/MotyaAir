package motyaair.services;

import motyaair.dto.UserDto.UpdateUserDto;
import motyaair.exceptions.ConditionsNotMetException;
import motyaair.exceptions.NotFoundException;
import motyaair.models.User;
import motyaair.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User addUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ConditionsNotMetException("Пользователь с такой почтой уже есть!");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User changeUser(Long userId, UpdateUserDto userData) {
        User updatedUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден!"));
        String firstName = userData.getFirstName();
        String lastName = userData.getLastName();
        String email = userData.getEmail();
        if (userRepository.findByEmail(email) != null) {
            throw new ConditionsNotMetException("Пользователь с такой почтой уже есть!");
        }


        if (firstName != null && !firstName.isBlank()) updatedUser.setFirstName(firstName);
        if (lastName != null && !lastName.isBlank()) updatedUser.setLastName(lastName);
        if (email != null && !email.isBlank()) updatedUser.setEmail(email);

        return userRepository.save(updatedUser);

    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден!"));
        userRepository.deleteById(userId);
    }
}
