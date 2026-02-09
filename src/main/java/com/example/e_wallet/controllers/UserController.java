package com.example.e_wallet.controllers;

import com.example.e_wallet.Models.UserModels;
import com.example.e_wallet.Models.WalletModel;
import com.example.e_wallet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
   public List<UserModels> getUsers(){
        return userService.getUsers();
   }

   @GetMapping("/{id}")
   public UserModels getUserById(@PathVariable  Long id){
    return userService.getUserById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id " + id));
   }

   @PostMapping("/create")
    public UserModels createUser(@RequestBody UserModels user){
        return  userService.createUser(user);
   }

   @PutMapping("/{id}")
    public UserModels updateUser(@PathVariable Long id,@RequestBody UserModels user){
        return  userService.updateUser(id, user);
   }

   @DeleteMapping("/{id}")
    public String  deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return "User with id "+ id +" has been deleted.";
   }

}
