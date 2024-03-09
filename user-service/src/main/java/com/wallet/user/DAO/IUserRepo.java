package com.wallet.user.DAO;

import com.wallet.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<UserEntity,Long> {
    //public UserEntity findByEmailId(String emailId);
}
