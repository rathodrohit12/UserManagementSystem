package com.rohit.usermanagementsystem.service;

import com.rohit.usermanagementsystem.dto.UserNoPasswordDTO;
import com.rohit.usermanagementsystem.entity.UserEntity;
import com.rohit.usermanagementsystem.dto.User;
import com.rohit.usermanagementsystem.repository.UserRepository;
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
    public User loginUser(String email, String pass) {
        UserEntity entity = userRepository.findByEmail(email);
        //if (entity != null && passwordEncoder.matches(pass, entity.getPass()))
        if (entity != null && pass.equals(entity.getPass())) {
            return modelMapper.map(entity, User.class);
        }
        return null;
    }


    @Override
    public void updateUser(Long id, UserNoPasswordDTO user) {
            UserEntity entity = userRepository.findById(id).get();
            entity.setName(user.getName());
            entity.setEmail(user.getEmail());
            entity.setMobile(user.getMobile());

            userRepository.save(entity);
    }

    @Override
    public void deleteUser(Long id) {
          userRepository.deleteById(id);
    }


    @Override
    public List<User> getAllUsers() {
        List<User> userList = Arrays.asList(modelMapper.map(userRepository.findAll(), User[].class));
        System.out.println(userList);
        return userList;
    }

    @Override
    public User getUserById(Long id) {
        return modelMapper.map(userRepository.findById(id).get(), User.class);
    }

    @Override
    public User getUserByEmail(String email) {
        return modelMapper.map(userRepository.findByEmail(email), User.class);
    }

    @Override
    public User getUserByMobile(String mobile) {
        return modelMapper.map(userRepository.findByMobile(mobile), User.class);
    }

    @Override
    public UserNoPasswordDTO convertToUpdateDTO(User user) {
        return modelMapper.map(user, UserNoPasswordDTO.class);
    }


}
