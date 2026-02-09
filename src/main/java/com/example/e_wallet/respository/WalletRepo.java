package com.example.e_wallet.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import  com.example.e_wallet.Models.WalletModel;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends JpaRepository<WalletModel,Long> {


}
