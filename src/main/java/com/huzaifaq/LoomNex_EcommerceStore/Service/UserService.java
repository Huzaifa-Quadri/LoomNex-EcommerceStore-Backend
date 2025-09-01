package com.huzaifaq.LoomNex_EcommerceStore.Service;

import com.huzaifaq.LoomNex_EcommerceStore.Model.User;
import com.huzaifaq.LoomNex_EcommerceStore.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepository;

    public User registerUser(User reguser) {
        try{
            System.out.println("User Registered Successfully");
            return userRepository.save(reguser);
        } catch (Exception e) {
            System.out.println("User Not Registered, Some Error Occured - ");
            e.printStackTrace();
        }
        return null;
    }

    public User loginUser(String email, String password) {
        try{
            User foundUser = userRepository.findByEmail(email);
            if (foundUser != null && foundUser.getPassword().equals(password)) {
                System.out.println("Welcome Sir/Mam "+ foundUser.getName()+". ");
                return foundUser;
            }else {
                System.out.println("Invalid Credentials");
            }
        } catch (Exception e) {
            System.out.println("No such User found !");
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getallUsers() {
        return userRepository.findAll();
    }
}
