package com.example.e_wallet.service;

import com.example.e_wallet.Models.TransactionType;
import com.example.e_wallet.Models.WalletModel;
import com.example.e_wallet.Models.Transaction;
import com.example.e_wallet.respository.TransactionRepo;
import com.example.e_wallet.respository.WalletRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final WalletRepo walletRepo;

    public TransactionService(TransactionRepo transactionRepo, WalletRepo walletRepo) {
        this.transactionRepo = transactionRepo;
        this.walletRepo = walletRepo;
    }

    @Transactional
    public Transaction deposit(Long walletId, double amount) {
        WalletModel wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance() + amount);
        walletRepo.save(wallet);

        Transaction tx = new Transaction();
        tx.setAmount(amount);
        tx.setTransactionType(TransactionType.SUCCESS);
        tx.setReference("DEP-" + System.currentTimeMillis()); // Use String
        tx.setCreatedAt(new Date());
        tx.setWallet(wallet);

        return transactionRepo.save(tx);
    }

    @Transactional
    public Transaction withdraw(Long walletId, double amount) {
        WalletModel wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);
        walletRepo.save(wallet);
        Transaction tx = new Transaction();
        tx.setAmount(amount);
        tx.setTransactionType(TransactionType.SUCCESS);
        tx.setReference("WTH-" + System.currentTimeMillis());
        tx.setCreatedAt(new Date());
        tx.setWallet(wallet);

        return transactionRepo.save(tx);
    }

    @Transactional
    public void transfer(Long senderWalletId, Long receiverWalletId, double amount) {
        WalletModel senderWallet = walletRepo.findById(senderWalletId)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        WalletModel receiverWallet = walletRepo.findById(receiverWalletId)
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        if (senderWallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        senderWallet.setBalance(senderWallet.getBalance() - amount);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);

        walletRepo.save(senderWallet);
        walletRepo.save(receiverWallet);

        Transaction debitTx = new Transaction();
        debitTx.setAmount(amount);
        debitTx.setTransactionType(TransactionType.SUCCESS);
        debitTx.setReference("TRF-OUT-" + System.currentTimeMillis());
        debitTx.setCreatedAt(new Date());
        debitTx.setWallet(senderWallet);

        Transaction creditTx = new Transaction();
        creditTx.setAmount(amount);
        creditTx.setTransactionType(TransactionType.SUCCESS);
        creditTx.setReference("TRF-IN-" + System.currentTimeMillis());
        creditTx.setCreatedAt(new Date());
        creditTx.setWallet(receiverWallet);

        transactionRepo.save(debitTx);
        transactionRepo.save(creditTx);
    }
}
