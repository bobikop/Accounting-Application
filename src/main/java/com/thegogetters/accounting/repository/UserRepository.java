package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Category;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    List<User> findAll();
    boolean existsByUsername (String username);

    //------------------------------------------------------------------------
//    List<User> findAllByRoleDescriptionAndCompanyOrderByCompanyTitleAscRoleDescription(String role, Company company);

//    @Query("SELECT u FROM User u WHERE u.role =?1 and u.company.title = ?2 order by u.company.title, u.role.description asc")
//    List<User> getRoleCompany(String role, Company company);



    //---------------------------------------

    List<User> findAllByCompanyOrderByRoleDescription(Company company);

    List<User> findAllByRoleDescriptionOrderByCompanyTitle(String role);



}
