package com.senai.pa4.repository;

import com.senai.pa4.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUsername(String name);  // INCLUÍDO NO PA4

    Optional<User> findByUsername(String name);  // INCLUÍDO NO PA4

}
