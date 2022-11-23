package org.springframework.samples.dwarf.tablero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TableroRepositoryTest {

    @Autowired
    TableroRepository tarepo;

    @Test
    public void initialDataFindAllCartasTest() {
        List<Carta> cartas = tarepo.findAllCartas();
        assertNotNull(cartas);
        assertFalse(cartas.isEmpty());
        assertEquals(63, cartas.size());
    }
}
