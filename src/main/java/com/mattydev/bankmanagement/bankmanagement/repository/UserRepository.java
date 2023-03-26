package com.mattydev.bankmanagement.bankmanagement.repository;

import com.mattydev.bankmanagement.bankmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author matty - 26/03/2023
 * @project bank-management
 */
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT * FROM USERS where email = ?1",nativeQuery = true)
    List<User> findByEmail(String emailValue);
}