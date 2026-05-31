package com.internship_code.travelmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.internship_code.travelmanagement.entity.User;
import com.internship_code.travelmanagement.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepo;


    @GetMapping("/register")
    public String registerPage()
    {
        return "register";
    }


    @PostMapping("/saveUser")
public String saveUser(User u)
{
    u.setRole("USER");

    userRepo.save(u);

    return "redirect:/login?registered";
}


    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }

    @PostMapping("/checkLogin")
public String checkLogin(
        @RequestParam String email,
        @RequestParam String password,
        HttpSession session)
{

    User u =
            userRepo.findByEmail(email);

        if(u != null &&
    u.getPassword().equals(password))
    {
        session.setAttribute(
                "loggedUser",
                u);

        session.setAttribute(
                "role",
                u.getRole());

            if(u.getRole().equals("ADMIN"))
        {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/";
        }

    return "redirect:/login?error";
}

@GetMapping("/logout")
public String logout(
        HttpSession session)
{
    session.invalidate();

    return "redirect:/";
}

}