package com.spring.universidad.cryptop2p.modelo.entidades.repository;

import com.spring.universidad.cryptop2p.modelo.entidades.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("repositoryUser")
public interface  UserRepository extends CrudRepository<User, Integer> {
}

