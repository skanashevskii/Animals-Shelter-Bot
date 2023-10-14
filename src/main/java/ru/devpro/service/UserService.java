package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.model.Animal;
import ru.devpro.model.User;
import ru.devpro.repositories.UsersRepository;

import java.util.Collection;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User createUser(User user) {
        LOGGER.info("Was invoked method for create animal: {}", user);
        return usersRepository.save(user);
    }

    public User editUser(User user) {
        LOGGER.info("Was invoked method for edit animal : {}", user);
        return usersRepository.findById(user.getId())
                .map(dbEntity -> {
                    dbEntity.setName(user.getName());
                    dbEntity.setFamily(user.getFamily());
                    dbEntity.setEmail(user.getEmail());
                    dbEntity.setRole(user.getRole());
                    dbEntity.setTelephone(user.getTelephone());

                    usersRepository.save(dbEntity);
                    return dbEntity;
                })
                .orElse(null);
    }

    public void deleteUser(long userId) {
        LOGGER.info("Was invoked method for delete animal by id: {}", userId);
        usersRepository.findById(userId)
                .map(entity -> {
                    usersRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    public User findUserById(Long userId) {
        LOGGER.info("Was invoked method for delete animal by id: {}", userId);
        return usersRepository.findById(userId).orElse(null);
    }

    public Collection<User> findAll() {
        return usersRepository.findAll();
    }


}
