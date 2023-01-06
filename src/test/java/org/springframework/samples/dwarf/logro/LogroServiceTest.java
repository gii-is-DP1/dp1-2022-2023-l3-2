package org.springframework.samples.dwarf.logro;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dwarf.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LogroServiceTest {
    
    @Autowired
    protected LogroService logroService;

    @Test
    public void shouldFindAll() {
        List<Logro> logros = logroService.findAll();

        Logro logro1 = EntityUtils.getById(logros, Logro.class, 1);
		assertThat(logro1.getName()).isEqualTo("10 victorias");
        assertThat(logro1.getDescripcion()).isEqualTo("Se consigue al ganar 10 partidas");
        assertThat(logro1.getDificultad()).isEqualTo(1);
        assertThat(logro1.getRequisito()).isEqualTo(10);
        //assertThat(logro1.getTipo()).isEqualTo(0);
		Logro logro5 = EntityUtils.getById(logros, Logro.class, 5);
		assertThat(logro5.getName()).isEqualTo("100 hierro");
        assertThat(logro5.getDescripcion()).isEqualTo("Se consigue al coleccionar 100 de hierro");
        assertThat(logro5.getDificultad()).isEqualTo(1);
        assertThat(logro5.getRequisito()).isEqualTo(100);
        //assertThat(logro5.getTipo()).isEqualTo(1);
    }
}