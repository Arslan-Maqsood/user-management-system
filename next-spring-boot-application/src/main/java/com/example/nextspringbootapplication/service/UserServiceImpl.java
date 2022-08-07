package com.example.nextspringbootapplication.service;

import com.example.nextspringbootapplication.entity.UserEntity;
import com.example.nextspringbootapplication.model.User;
import com.example.nextspringbootapplication.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        userRepository.save(userEntity);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
       List<UserEntity> usersEntities =  userRepository.findAll();

       List<User> users = usersEntities
               .stream().map(userEntity -> new User(
                       userEntity.getId(),
                       userEntity.getFirstName(),
                       userEntity.getLastName(),
                       userEntity.getEmailId()
               )).collect(Collectors.toList());
       return users;
    }

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = userRepository.getReferenceById(id);
        User user = new User();
        BeanUtils.copyProperties(userEntity,user);

        return user;
    }

    @Override
    public boolean deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        userRepository.delete(userEntity);
        return true;
    }

    @Override
    public User updateUser(Long id, User user) {
        UserEntity userEntity = userRepository.findById(id).get();

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmailId(user.getEmailId());

        userRepository.save(userEntity);
        return user;
    }
}
