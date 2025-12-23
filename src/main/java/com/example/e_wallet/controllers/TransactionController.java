package com.example.e_wallet.controllers;

import com.example.e_wallet.Models.Transaction;
import com.example.e_wallet.service.TransactionService;
import com.example.e_wallet.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transact")
public class TransactionController {

    public final TransactionService transactionService;
    public final WalletService walletService;

    public TransactionController(TransactionService transactionService, WalletService walletService, WalletService walletService1) {
        this.transactionService = transactionService;
        this.walletService = walletService1;
    }


    @PostMapping("/deposit/{walletId}/{amount}")
    public Transaction deposit(@PathVariable Long walletId, @PathVariable Double amount) {
        return transactionService.deposit(walletId, amount);
    }

     @PostMapping("/widthdraw/{walletId}/{amount}")
    public Transaction withdraw(@PathVariable Long walletId, @PathVariable Double amount) {
        return transactionService.withdraw(walletId, amount);
     }

     @PostMapping("/tranfer/{senderId}/{receiverId}/{amount}")
    public String transfer(@PathVariable Long senderId, @PathVariable Long receiverId, @PathVariable Double amount) {
        transactionService.transfer(senderId, receiverId, amount);
        return "Transfer successfully";
     }
}
