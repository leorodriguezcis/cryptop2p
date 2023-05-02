package com.spring.universidad.cryptop2p.services.interfaces;

import java.util.Optional;

public interface GenericDAO<E> {

    Optional<E> findById(Integer id);
    E save(E generico);
    Iterable<E> findAll();
    void deleteById(Integer id);


}
