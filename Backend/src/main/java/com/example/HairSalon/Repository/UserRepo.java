package com.example.HairSalon.Repository;

import com.example.HairSalon.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.username= :username")
    User getUserByUsername(@Param("username") String username);

    @Query("select  u from User u where u.email= :email")
    User getUserByEmail(@Param("email") String email);

    @Query("select u from User u where u.role=:roleName")
    List<User> getAllUsersByRoleName(@Param("roleName")String roleName);
}

