package com.nakomans.economizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nakomans.economizza.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

}
