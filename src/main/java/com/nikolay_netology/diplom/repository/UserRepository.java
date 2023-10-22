package com.nikolay_netology.diplom.repository;

import com.nikolay_netology.diplom.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserData, String> {
    UserData findByLogin(String login);
}
