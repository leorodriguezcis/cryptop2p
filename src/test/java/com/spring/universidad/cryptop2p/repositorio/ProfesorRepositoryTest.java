package com.spring.universidad.cryptop2p.repositorio;

import datos.DatosDummy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProfesorRepositoryTest {

    @Autowired
    @Qualifier("repositorioProfesores")
    PersonaRepository profesorRepository;
    @Autowired
    CarreraRepository carreraRepository;

    @Test
    void buscarProfesoresPorCarrera() {

        List<Persona> personas = (List<Persona>) profesorRepository.saveAll(Arrays.asList(DatosDummy.profesor01(), DatosDummy.profesor02()));
       carreraRepository.saveAll(Arrays.asList(DatosDummy.carrera4(), DatosDummy.carrera5()));
       Set<Carrera> carreras = Set.of(DatosDummy.carrera4(),DatosDummy.carrera5());
        personas.forEach(profesor -> ((Profesor)profesor).setCarreras(carreras));
        profesorRepository.saveAll(personas);
        List<Persona> expected = (List<Persona>) ((ProfesorRepository)profesorRepository).buscarProfesoresPorCarrera(DatosDummy.carrera1().getNombre());
        assertEquals(expected.size(), 2);

    }
}