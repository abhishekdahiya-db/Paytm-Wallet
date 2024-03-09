package com.wallet.DAO;

import com.wallet.entity.UserWallet;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWalletRepo extends JpaRepository<UserWallet,Long> {
    public UserWallet findByUserId(long userId);
}
