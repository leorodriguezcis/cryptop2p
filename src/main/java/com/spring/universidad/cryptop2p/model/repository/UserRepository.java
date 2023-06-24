package com.spring.universidad.cryptop2p.model.repository;

import com.spring.universidad.cryptop2p.model.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("repositoryUser")
public interface  UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByName(String userName);
}

