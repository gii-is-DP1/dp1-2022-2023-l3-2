package org.springframework.samples.dwarf.tablero;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class TableroServiceTest {
    
    @Autowired
    protected TableroService tableroService;

    @Mock
    TableroRepository tableroRepository;

    Tablero tablero;
    Mazo mazo;

    @BeforeEach
    public void config() {
        tablero = new Tablero();
        mazo = new Mazo();
        List<Mazo> mazos = new ArrayList<>();
        mazos.add(mazo);
        tablero.setMazos(mazos);
        when(tableroRepository.findById(any(Integer.class))).thenReturn(tablero);
    }

    // No funciona por que no tiene restricciones ni excepcion.
    @Test
    public void saveTestUnsucessful() {
        Tablero tablero2 = new Tablero();
        tablero2.setName("tablero prueba");
        tablero2.setId(1);
        TableroService tableroService = new TableroService(tableroRepository);
        assertThrows(Exception.class, () -> tableroService.saveTablero(tablero2));
    }

    @Test
    public void saveTestSucessful() {
        Tablero tablero2 = new Tablero();
        tablero2.setName("tablero prueba");
        tablero2.setId(1);
        TableroService tableroService = new TableroService(tableroRepository);
        try{
            tableroService.saveTablero(tablero2);
        }catch(Exception e) {
            fail("This expeception should not be thrown!");
        }
    }


}
