package org.springframework.samples.dwarf.jugador;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dwarf.user.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class JugadorServiceTest {
    
    @Autowired
    protected JugadorService jugadorService;

    @BeforeEach
    public void config(){
        Jugador jug = new Jugador();
        User user = new User();

        user.setUsername("TestUser");
        user.setEnabled(true);

        jug.setId(999);
        jug.setUser(user);
    }

    /* @Test
    public void shouldFindOwnerById() {
        Jugador jugador = this.jugadorService.findOwnerById(999);
		assertThat(jugador.getUser().getUsername().equals("TestUser"));
		Jugador jugador1 = this.jugadorService.findOwnerById(154);
		assertThat(jugador1).isNull();
    } */

}

