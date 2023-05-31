package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.services.interfaces.GenericDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GenericDAOImpl<E, R extends CrudRepository<E, Integer> > implements GenericDAO<E> {


    protected final R repo;

    public GenericDAOImpl(R repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public E save(E generico) {
        return repo.save(generico);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
