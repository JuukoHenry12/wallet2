package com.example.e_wallet.service;

import com.example.e_wallet.Models.WalletModel;
import com.example.e_wallet.respository.WalletRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WalletService {

    private final WalletRepo walletRepo;

    public WalletService(WalletRepo walletRepo) {
        this.walletRepo = walletRepo;
    }

    public List<WalletModel> getWallets(){
        return  walletRepo.findAll();
    }

    public WalletModel getWalletById(Long id){
        return walletRepo.findById(id).get();
    }

    public WalletModel createWallet(WalletModel wallet){
        return walletRepo.save(wallet);
    }

    public WalletModel updateWallet(WalletModel wallet){
        return walletRepo.save(wallet);
    }

    public void deleteWalletById(Long id){
        walletRepo.deleteById(id);
    }
}
