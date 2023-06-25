package com.spring.universidad.cryptop2p.services.implementation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GenericService<E, R extends CrudRepository<E, Integer> > {


    protected final R repo;

    public GenericService(R repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Optional<E> findById(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public E save(E generico) {
        return repo.save(generico);
    }

    @Transactional(readOnly = true)
    public Iterable<E> findAll() {
        return repo.findAll();
    }
    @Transactional
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
