package com.spring.universidad.cryptop2p.modelo.entities.controller;

import com.spring.universidad.cryptop2p.services.interfaces.GenericDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericController<E, S extends GenericDAO<E>>{

    protected final S service;
    protected String nombreEntidad;

    public GenericController(S service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<?> obtenerTodos(){
        Map<String, Object> message = new HashMap<>();
        List<E> res = (List<E>)service.findAll();
        if(res.isEmpty()){
            //throw new BadRequestException(String.format("no hay ninguna entidad %ss", nombreEntidad));
            message.put("succes", Boolean.FALSE);
            message.put("message", String.format("no existe ninguna entidad del tipo %s", nombreEntidad));
            return ResponseEntity.badRequest().body(message);
        }
        message.put("succes", Boolean.TRUE);
        message.put("datos", res);
        return ResponseEntity.ok(message);
    }
}
