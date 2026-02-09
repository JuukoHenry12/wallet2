package com.example.e_wallet.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name="users")
public class UserModels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserId;

    @Column(nullable = false)
    private  String userName;

    @Column(nullable = false)
    private  String password;

    @Column(nullable = false)
    private  String email;

    @Column(nullable = false)
    private  String phoneNumber;

    @Column(nullable = false)
    private  String KYC_Status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createTime;

    @OneToOne(mappedBy ="user",cascade = CascadeType.ALL)
    private WalletModel wallet;

    @PrePersist
    protected void onCreate() {
        this.createTime = new Date();
    }

}
