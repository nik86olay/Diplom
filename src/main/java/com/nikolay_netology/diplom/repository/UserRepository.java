package com.nikolay_netology.diplom.repository;

import com.nikolay_netology.diplom.model.UserDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDate, String> {
    UserDate findByLogin(String userName);
}
