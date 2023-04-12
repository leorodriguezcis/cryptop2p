package com.spring.universidad.cryptop2p.repositorio;

import datos.DatosDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AulaRepositoryTest {

    @BeforeEach
    void setUp() {
        aulaRepository.save(DatosDummy.aula1);
        aulaRepository.save(DatosDummy.aula2);
        aulaRepository.save(DatosDummy.aula3);

    }

    @Autowired
    AulaRepository aulaRepository;
    @Autowired
    PabellonRepository pabellonRepository;



    @Test
    void findAulaByTipoPizzarron() {
        List<Aula> expected = (List<Aula>) aulaRepository.findAulaByTipoPizzarron(TipoPizzarron.PIZARRA_MARCADOR);
        assertThat(expected.size() > 1).isTrue();
    }

    @Test
    void findAulaByPabellon_Nombre() {
        DatosDummy.pabellonA().setAulas(Set.of(DatosDummy.aula1,DatosDummy.aula2,DatosDummy.aula3));
        pabellonRepository.save(DatosDummy.pabellonA());
        List<Aula> expected = (List<Aula>) aulaRepository.findAulaByPabellon_Nombre(DatosDummy.pabellonA().getNombre());
        assertThat(expected.isEmpty()).isTrue();

    }

    @Test
    void findAulaByNumeroAula() {
        List<Aula> expected = (List<Aula>) aulaRepository.findAulaByNumeroAula(1);
        assertThat(expected.size() == 1).isTrue();
    }
}