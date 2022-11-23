package org.springframework.samples.dwarf.jugador;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class JugadorServiceTest {
    @Mock
    JugadorRepository jurepo;


    Jugador jugador;

    @BeforeEach
    public void config(){
        jugador = new Jugador();

        jugador.setFirstName("PruebaJugador");
        when(jurepo.findById(any(Integer.class))).thenReturn(jugador);
    }
    
}
