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

import com.mysql.cj.xdevapi.Table;

@ExtendWith(MockitoExtension.class)
public class TableroServiceTest {
    
    @Mock
    TableroRepository tarepo;


    Tablero tablero;

    @BeforeEach
    public void config(){
        tablero = new Tablero();

        Mazo mazo = new Mazo();
        List<Mazo> mazos = new ArrayList<>();
        mazos.add(mazo);
        tablero.setMazos(mazos);
        when(tarepo.findById(any(Integer.class))).thenReturn(tablero);
    }
    // No funciona por que no tiene restricciones ni excepcion.
    @Test
    public void saveTestUnsucessful(){
        Tablero table = new Tablero();
        table.setName("prueba");
        table.setId(1);
        TableroService tableroService = new TableroService(tarepo);
        assertThrows(Exception.class, () -> tableroService.saveTablero(table));
    }
    // No funciona por que no tiene restricciones ni excepcion.
    @Test
    public void saveTestSucessful(){
        Tablero table = new Tablero();
        table.setName("prueba");
        table.setId(1);
        TableroService tableroService = new TableroService(tarepo);
        try{
            tableroService.saveTablero(table);
        }catch(Exception e) {
            
        }
    }

}
