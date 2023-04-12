package com.spring.universidad.cryptop2p.servicios.implementaciones;

import com.spring.universidad.cryptop2p.repositorio.CarreraRepository;
import com.spring.universidad.cryptop2p.servicios.contratos.CarreraDAO;
import datos.DatosDummy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class CarreraDAOImplTest {

    CarreraDAO carreraDAO;
    CarreraRepository carreraRepository;

    @BeforeEach
    void setUp() {
        carreraRepository = mock(CarreraRepository.class);
        carreraDAO = new CarreraDAOImpl(carreraRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findCarrerasByNombreContains() {
        when(carreraRepository.findCarrerasByNombreContains("Ingenieria"))
                .thenReturn(Arrays.asList(DatosDummy.carrera4(),DatosDummy.carrera6()));
        List<Carrera> expected = (List<Carrera>) carreraDAO.findCarrerasByNombreContains("Ingenieria");

        assertThat(expected.get(0)).isEqualTo(DatosDummy.carrera4());
        assertThat(expected.get(1)).isEqualTo(DatosDummy.carrera6());

        verify(carreraRepository).findCarrerasByNombreContains("Ingenieria");
    }

    @Test
    void findCarrerasByNombreContainsIgnoreCase() {
        when(carreraRepository.findCarrerasByNombreContainsIgnoreCase("ingenieria"))
                .thenReturn(Arrays.asList(DatosDummy.carrera4(),DatosDummy.carrera6()));
        List<Carrera> expected = (List<Carrera>) carreraDAO.findCarrerasByNombreContainsIgnoreCase("ingenieria");

        assertThat(expected.get(0)).isEqualTo(DatosDummy.carrera4());
        assertThat(expected.get(1)).isEqualTo(DatosDummy.carrera6());

        verify(carreraRepository).findCarrerasByNombreContainsIgnoreCase("ingenieria");
    }

    @Test
    void findCarrerasByCantidadAnios() {
        when(carreraRepository.findCarrerasByCantidadAnios(6))
                .thenReturn(Arrays.asList(DatosDummy.carrera5(),DatosDummy.carrera6()));
        List<Carrera> expected = (List<Carrera>) carreraDAO.findCarrerasByCantidadAnios(6);

        assertThat(expected.get(0)).isEqualTo(DatosDummy.carrera5());
        assertThat(expected.get(1)).isEqualTo(DatosDummy.carrera6());

        verify(carreraRepository).findCarrerasByCantidadAnios(6);
    }
}