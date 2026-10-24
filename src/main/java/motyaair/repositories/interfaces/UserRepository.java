package motyaair.repositories.interfaces;

import motyaair.models.User;

public interface UserRepository {

    User addUser (User user);

    User changeUser(User user, Long requestorId);

    void deleteUser(Long userId, Long requestorId);

}
