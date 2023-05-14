package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.EUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EUserRepository extends JpaRepository<EUser, Long>, EUserRepositoryCustom {
    @Query("SELECT u FROM EUser u WHERE u.userName =:userName AND u.isDeleted = false ")
    EUser findByUserName(@Param("userName") String userName);

    @Query("SELECT u FROM EUser u WHERE u.userId =:userId AND u.password =:password AND u.isDeleted = false ")
    EUser findByUserIdAndPassword(Long userId, String password);

    @Query("SELECT u FROM EUser u WHERE u.userName =:userName AND u.password =:password AND u.isDeleted = false ")
    EUser findByUserNameAndPassword(String userName, String password);

    @Query("SELECT u FROM EUser u WHERE u.userId =:userId AND u.isDeleted = false ")
    EUser findByUserId(Long userId);
}
