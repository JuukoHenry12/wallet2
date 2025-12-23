package com.example.e_wallet.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import com.example.e_wallet.Models.Transaction;


@Setter
@Getter
@Entity
@Table(name="wallets")
public class WalletModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Column(nullable = false)
    private double balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId",nullable = false)
    @JsonIgnore
    private  UserModels user;


    @OneToMany(
            mappedBy = "wallet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Transaction> transactions;

}
