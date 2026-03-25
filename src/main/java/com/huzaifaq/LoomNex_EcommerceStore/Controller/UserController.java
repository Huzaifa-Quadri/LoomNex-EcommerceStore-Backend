package com.huzaifaq.LoomNex_EcommerceStore.Controller;

import com.huzaifaq.LoomNex_EcommerceStore.Service.UserService;
import com.huzaifaq.LoomNex_EcommerceStore.dto.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Only allow admins to view all users. You'd need to add roles to your User model for this to fully work.
    // For now, mapping this to ensure it's at least secured. 
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }
}