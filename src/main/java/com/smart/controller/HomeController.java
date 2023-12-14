package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model m) {
        m.addAttribute("title", "Home- Smart Contact Manager");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model m) {
        m.addAttribute("title", "About- Smart Contact Manager");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model m) {
        m.addAttribute("title", "Register- Smart Contact Manager");
        m.addAttribute("user", new User());
        return "signup";
    }

    //Handler for registering user
    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {
        try {
            if (!agreement) {
                System.out.println("You have not agreed the terms and conditions");
                throw new Exception("You have not agreed the terms and conditions");
            }
            if (result1.hasErrors()) {
                System.out.println("ERROR" + result1.toString());
                model.addAttribute("user", user);
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            System.out.println(agreement);
            System.out.println(user);
            User result = this.userRepository.save(user);
            System.out.println(result);
            model.addAttribute("user", user);
            Message message = new Message();
            message.setContent("Registered Successfully !!");
            message.setType("alert-success");
            session.setAttribute("message", message);
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setContent("Something went wrong !!" + e.getMessage());
            message.setType("alert-danger");
            session.setAttribute("message", message);
            return "signup";
        }

    }
    
    
    //handler for custom login
    @GetMapping("/signin")
    public String customLogin(Model model){
        model.addAttribute("title","Login Page");
        return "login";
    }
}


