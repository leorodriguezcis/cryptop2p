package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.services.interfaces.GenericDAO;

public class GenericController<E, S extends GenericDAO<E>>{

    protected final S service;
    protected String nombreEntidad;

    public GenericController(S service) {
        this.service = service;
    }

}
