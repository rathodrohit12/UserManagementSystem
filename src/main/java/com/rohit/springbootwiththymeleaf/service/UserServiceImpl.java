package com.rohit.springbootwiththymeleaf.service;

import com.rohit.springbootwiththymeleaf.entity.UserEntity;
import com.rohit.springbootwiththymeleaf.model.User;
import com.rohit.springbootwiththymeleaf.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerUser(User user) {
        //user.setPass(passwordEncoder.encode(user.getPass()));
        userRepository.save(modelMapper.map(user, UserEntity.class));
    }


    @Override
    public UserEntity loginUser(String email, String pass) {
        UserEntity entity = userRepository.findByEmail(email);
//        if (entity != null && passwordEncoder.matches(pass, entity.getPass())) {
//            return entity;
//        }
        if (entity != null && pass.equals(entity.getPass())) {
            return entity;
        }
        return null;
    }


    @Override
    public void updateUser(int id, User user) {
            UserEntity entity = userRepository.findById(id).get();
            entity.setName(user.getName());
            entity.setEmail(user.getEmail());
            entity.setMobile(user.getMobile());
            entity.setPass(user.getPass());
            userRepository.save(entity);
    }

    @Override
    public void deleteUser(int id) {
          userRepository.deleteById(id);
    }


    @Override
    public List<User> getAllUsers() {

        List<User> userList = Arrays.asList(modelMapper.map(userRepository.findAll(), User[].class));
        System.out.println(userList);
        return userList;
    }

    @Override
    public UserEntity getUserById(int id) {
        return userRepository.findById(id).get();
    }


}
