package com.example.e_wallet.controllers;

import com.example.e_wallet.Models.WalletModel;
import com.example.e_wallet.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public List<WalletModel> getWallets(){
        return walletService.getWallets();
    }

    @PostMapping("/create")
    public WalletModel createWallet(WalletModel wallet){
        return walletService.createWallet(wallet);
    }

    @PutMapping
    public WalletModel updateWallet(WalletModel wallet){
        return walletService.updateWallet(wallet);
    }

    @DeleteMapping("/{id}")
    public String deleteWallet(WalletModel wallet, @PathVariable  Long id){
        walletService.deleteWalletById(id);
        return "wallet has been deleted successfully";
    }
}
