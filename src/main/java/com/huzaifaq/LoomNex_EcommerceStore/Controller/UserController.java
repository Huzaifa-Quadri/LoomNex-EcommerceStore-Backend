package com.huzaifaq.LoomNex_EcommerceStore.Controller;

import com.huzaifaq.LoomNex_EcommerceStore.Model.User;
import com.huzaifaq.LoomNex_EcommerceStore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")  //We are allowing everyone as we are not using Spring Security yet
public class UserController {
    @Autowired
    private UserService userService;

     @PostMapping("/register")
     public User registerUser(@RequestBody User reguser){
         return userService.registerUser(reguser);
     }

    @PostMapping("/login")
    public User loginUserService(@RequestBody User loginUser) {
        return userService.loginUser(loginUser.getEmail(), loginUser.getPassword());
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getallUsers();
    }
}