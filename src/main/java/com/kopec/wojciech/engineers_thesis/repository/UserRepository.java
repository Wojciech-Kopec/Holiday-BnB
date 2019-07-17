package com.kopec.wojciech.engineers_thesis.repository;

import com.kopec.wojciech.engineers_thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource()
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAllByUsernameContainingIgnoreCaseOrderByUsernameAsc(String username);
}
