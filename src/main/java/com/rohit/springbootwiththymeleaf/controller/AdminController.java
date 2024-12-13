package com.rohit.springbootwiththymeleaf.controller;


import com.rohit.springbootwiththymeleaf.entity.UserEntity;
import com.rohit.springbootwiththymeleaf.model.User;
import com.rohit.springbootwiththymeleaf.repository.UserRepository;
import com.rohit.springbootwiththymeleaf.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AdminController(UserService userService, UserRepository userRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/all")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "all-users";
    }

}
