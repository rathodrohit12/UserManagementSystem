package com.rohit.springbootwiththymeleaf.controller;

import com.rohit.springbootwiththymeleaf.entity.UserEntity;
import com.rohit.springbootwiththymeleaf.model.User;
import com.rohit.springbootwiththymeleaf.repository.UserRepository;
import com.rohit.springbootwiththymeleaf.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login-page")
    public String showLoginForm() {
        return "login-page";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String pass, Model model, HttpSession session) {
        UserEntity entity = userService.loginUser(email, pass);
        if (entity != null) {
            session.setAttribute("userId", entity.getId());
            session.setAttribute("userName", entity.getName());
            session.setAttribute("userMobile", entity.getMobile());
            session.setAttribute("userEmail", entity.getEmail());
            return "profile-page";
        } else {
            model.addAttribute("msg", "Invalid email or password");
            model.addAttribute("msgType", "alert-danger");
            return "login-page";
        }
    }


    @GetMapping("/register-page")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register-page";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register-page";
        }else if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("msg", "Email already exists");
            model.addAttribute("msgType", "alert-danger");
            return "register-page";
        }else if (userRepository.findByMobile(user.getMobile()) != null) {
            model.addAttribute("msg", "Mobile number already exists");
            model.addAttribute("msgType", "alert-danger");
            return "register-page";
        }
        try {
            userService.registerUser(user);
            model.addAttribute("msg", "Registration successful!");
            model.addAttribute("msgType", "alert-success");
            return "login-page";

        } catch (Exception e) {
            model.addAttribute("msg", "Registration failed. Please try again.");
            model.addAttribute("msgType", "alert-danger");
            return "register-page";
        }
    }


    @GetMapping("/profile")
    public String showProfile(Model model) {
        return "profile-page";
    }


    @GetMapping ("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("msg", "You have been logged out.");
        model.addAttribute("msgType", "alert-success");
        return "login-page";
    }


    @GetMapping("/all")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "all-users";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        UserEntity entity = userService.getUserById(id);
        model.addAttribute("user", entity);
        return "update-page";
    }


    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable int id , @Valid User user,  BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "update-page";
        }
        try {
            userService.updateUser(id, user);
            model.addAttribute("msg", "Update successful!");
            model.addAttribute("msgType", "alert-success");
            return "redirect:/user/all";

        } catch (Exception e) {
            model.addAttribute("msg", "Email already exists. Update failed. Please try again.");
            model.addAttribute("msgType", "alert-danger");
            return "update-page";
        }

    }



    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, Model model) {
        userService.deleteUser(id);
        return "redirect:/user/all";
    }







}
