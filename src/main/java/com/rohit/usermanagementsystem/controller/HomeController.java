package com.rohit.usermanagementsystem.controller;

import com.rohit.usermanagementsystem.dto.User;
import com.rohit.usermanagementsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHomepage() {
        return "index";
    }


    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

    @GetMapping("/login-page")
    public String showLoginForm() {
        return "login-page";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String pass, Model model, HttpSession session) {
        User user = userService.loginUser(email, pass);
        if (user != null) {
            model.addAttribute("userId", user.getId());
            model.addAttribute("userName", user.getName());
            model.addAttribute("userMobile", user.getMobile());
            model.addAttribute("userEmail", user.getEmail());

            session.setAttribute("userId", user.getId());
            session.setAttribute("userMobile", user.getMobile());
            session.setAttribute("userEmail", user.getEmail());
            boolean isAdmin = "ROLE_ADMIN".equals(user.getRole());
            session.setAttribute("isAdmin", isAdmin);

            if (isAdmin) {
                return "admin-profile-page";
            } else {
                return "user-profile-page";
            }
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
        }
        if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("msg", "Email already exists");
            model.addAttribute("msgType", "alert-danger");
            return "register-page";
        }
        if (userService.getUserByMobile(user.getMobile()) != null) {
            model.addAttribute("msg", "Mobile number already exists");
            model.addAttribute("msgType", "alert-danger");
            return "register-page";
        }
        try {
            String roleName = user.getEmail().equals("admin@gmail.com") ? "ROLE_ADMIN" : "ROLE_USER";
            user.setRole(roleName);
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
    @GetMapping ("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("msg", "You have been logged out.");
        model.addAttribute("msgType", "alert-success");
        return "login-page";
    }



}
