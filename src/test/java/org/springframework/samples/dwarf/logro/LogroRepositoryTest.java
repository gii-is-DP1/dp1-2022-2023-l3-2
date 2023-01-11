package org.springframework.samples.dwarf.logro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LogroRepositoryTest {

    @Autowired
    LogroRepository logroRepository;

    @Test
    public void findAllTest() {
        List<Logro> logros = logroRepository.findAll();
        assertNotNull(logros);
        assertFalse(logros.isEmpty());
        assertEquals(19, logros.size());
    }
    
}
