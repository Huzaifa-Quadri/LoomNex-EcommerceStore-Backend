package com.huzaifaq.LoomNex_EcommerceStore.Controller;

import com.huzaifaq.LoomNex_EcommerceStore.Model.User;
import com.huzaifaq.LoomNex_EcommerceStore.Service.UserService;
import com.huzaifaq.LoomNex_EcommerceStore.dto.LoginRequest;
import com.huzaifaq.LoomNex_EcommerceStore.dto.RegisterRequest;
import com.huzaifaq.LoomNex_EcommerceStore.dto.UserDTO;
import com.huzaifaq.LoomNex_EcommerceStore.security.CustomUserDetailsService;
import com.huzaifaq.LoomNex_EcommerceStore.security.JwtService;

import org.springframework.beans.factory.annotation.Value;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    @Value("${app.security.cookie.secure:false}")
    private boolean isCookieSecure;

    public AuthController(AuthenticationManager authManager, JwtService jwtService, CustomUserDetailsService userDetailsService, UserService userService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping({"/auth/login", "/users/login"})
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest req, HttpServletResponse response) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            var userDetails = userDetailsService.loadUserByUsername(req.getEmail());
            String token = jwtService.generateToken(userDetails.getUsername(), Map.of("roles", userDetails.getAuthorities()));
            
            // Create HttpOnly Cookie
            Cookie jwtCookie = new Cookie("token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(isCookieSecure); 
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 1 day
            response.addCookie(jwtCookie);
            
            return ResponseEntity.ok(Map.of("message", "Login successful, token saved in cookies"));
        } catch (AuthenticationException ex) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PostMapping({"/auth/register", "/users/register"})
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest req, HttpServletResponse response) {
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        
        UserDTO savedUser = userService.registerUser(user);

        // Auto-login after registration
        var userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String token = jwtService.generateToken(userDetails.getUsername(), Map.of("roles", userDetails.getAuthorities()));

        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(isCookieSecure);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(Map.of(
            "message", "User registered and logged in successfully",
            "user", savedUser
        ));
    }
}
