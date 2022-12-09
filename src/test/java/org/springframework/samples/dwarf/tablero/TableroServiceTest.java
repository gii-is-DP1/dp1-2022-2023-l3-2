package org.springframework.samples.dwarf.tablero;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class TableroServiceTest {
        
    @Mock
    TableroRepository tableroRepo;

    Tablero tablero;
    Mazo mazo;

    @BeforeEach
    public void config(){

        mazo=new Mazo();
        tablero=new Tablero();
        tablero.setId(1);
    }

    @Test    
    public void saveTestSucessful(){
        TableroService tableroService = new TableroService(tableroRepo);
        try{
            tableroService.saveTablero(tablero);
        }catch(Exception e){
            fail("This expeception should not be thrown!");
        }

    }


    // No funciona por que no tiene restricciones ni excepcion.
    /* 
    @Test
    public void saveTestUnsucessful() {
        Tablero tablero2 = new Tablero();
        tablero2.setName("tablero prueba");
        tablero2.setId(1);
        TableroService tableroService = new TableroService(tableroRepo);
        assertThrows(Exception.class, () -> tableroService.saveTablero(tablero2));
    }
*/

}
