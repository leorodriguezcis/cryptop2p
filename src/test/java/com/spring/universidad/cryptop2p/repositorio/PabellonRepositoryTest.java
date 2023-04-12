package com.spring.universidad.cryptop2p.repositorio;

import datos.DatosDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PabellonRepositoryTest {

    @Autowired
    PabellonRepository pabellonRepository;

    @BeforeEach
    void setUp() {
        pabellonRepository.save(DatosDummy.pabellonA());
        pabellonRepository.save(DatosDummy.pabellonB());
        pabellonRepository.save(DatosDummy.pabellonC());

    }

    @Test
    void findPabellonByDireccion_Localidad() {
        List<Pabellon> expected = (List<Pabellon>) pabellonRepository.findPabellonByDireccion_Localidad(DatosDummy.pabellonA().getDireccion().getLocalidad());
        assertEquals(expected.size(), 3);
    }

    @Test
    void findPabellonByNombre() {
        List<Pabellon> expected = (List<Pabellon>) pabellonRepository.findPabellonByNombre(DatosDummy.pabellonA().getNombre());
        assertEquals(expected.size(),1);
    }
}