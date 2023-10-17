package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.devpro.dto.UserDTO;

import ru.devpro.mapers.UserMapper;

import ru.devpro.model.User;
import ru.devpro.repositories.UsersRepository;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.userMapper = UserMapper.INSTANCE;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        LOGGER.info("Received request to save shelter: {}", userDTO);
        User userEntity = userMapper.toEntity(userDTO); // Преобразуйте DTO в сущность
        User savedEntity = usersRepository.save(userEntity);
        return userMapper.toDTO(savedEntity); // Преобразуйте сущность обратно в DTO
    }
    @Override
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
    @Override
    public void deleteUser(Long userId) {
        LOGGER.info("Was invoked method for delete animal by id: {}", userId);
        usersRepository.findById(userId)
                .map(entity -> {
                    usersRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }
    @Override
    public User findUserById(Long userId) {
        LOGGER.info("Was invoked method for delete animal by id: {}", userId);
        return usersRepository.findById(userId).orElse(null);
    }
    @Override
    public Collection<User> findAll() {
        return usersRepository.findAll();
    }


}
