package com.example.e_wallet.service;

import com.example.e_wallet.Models.UserModels;
import com.example.e_wallet.Models.WalletModel;
import com.example.e_wallet.respository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserModels> getUsers(){
        return userRepo.findAll();
    }

    public Optional<UserModels> getUserById(Long id){
        return userRepo.findById(id);
    }

    public UserModels createUser(UserModels user){

        if(userRepo.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        if(userRepo.existsByPhoneNumber(user.getPhoneNumber())){
            throw new RuntimeException("Phone number already exists");
        }

        if(userRepo.existsByUserName(user.getUserName())){
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        WalletModel wallet = new WalletModel();
        wallet.setBalance(0);
        wallet.setUser(user);
        user.setWallet(wallet);

        return userRepo.save(user);
    }

    public  UserModels  updateUser(Long id, UserModels userDetails){
        return userRepo.findById(id).map(user->{
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setKYC_Status(userDetails.getKYC_Status());
            user.setUserName(userDetails.getUserName());
            user.setPhoneNumber(String.valueOf(userDetails.getPhoneNumber()));
            return userRepo.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUserById(Long id){
        if(!userRepo.existsById(id)){
            throw new RuntimeException("User not found with id " + id);
        }
        userRepo.deleteById(id);
    }

    public UserModels login(String email, String password){
        UserModels user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email " + email));
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Password doesn't match");
        }
        return user;
    }

}
