package com.spring.universidad.cryptop2p.repositorio;

import datos.DatosDummy;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CarreraRepositoryTest {

    @BeforeEach
    void setUp() {
        carreraRepository.save(DatosDummy.carrera1());
        carreraRepository.save(DatosDummy.carrera2());
        carreraRepository.save(DatosDummy.carrera3());
    }

    @AfterEach
    void tearDown() {
        carreraRepository.deleteAll();
    }

    @Autowired
    CarreraRepository carreraRepository;

    @Test
    @DisplayName("Buscar carreras con nombre")
    void findCarrerasByNombreContains() {

        Iterable<Carrera> expected = carreraRepository.findCarrerasByNombreContains("Ingenieria");
        assertThat(((List<Carrera>)expected).size() == 2).isTrue();
    }

    @Test
    @DisplayName("Buscar carreras con nombre sin importar mayus o minus")
    void findCarrerasByNombreContainsIgnoreCase() {

        Iterable<Carrera> expected = carreraRepository.findCarrerasByNombreContainsIgnoreCase("ingenieria");
        assertThat(((List<Carrera>)expected).size() == 2).isTrue();
    }

    @Test
    @DisplayName("Buscar carreras por cantidad de años")
    void findCarrerasByCantidadAnios() {

        Iterable<Carrera> expected = carreraRepository.findCarrerasByCantidadAnios(4);
        assertThat(((List<Carrera>)expected).size() == 1).isTrue();
    }
}