package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);
    List<User> findAll();
}
