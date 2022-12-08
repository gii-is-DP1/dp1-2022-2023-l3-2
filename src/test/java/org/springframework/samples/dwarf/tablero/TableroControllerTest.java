package org.springframework.samples.dwarf.tablero;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dwarf.configuration.SecurityConfiguration;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers = TableroController.class, 
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class TableroControllerTest {
    
    private static final int TEST_TABLERO_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TableroService taService;

    @MockBean
    private JugadorService jugadorService;

    private Tablero tableroPrueba;

    @BeforeEach
    void setup() {
        tableroPrueba = new Tablero();
        tableroPrueba.setId(TEST_TABLERO_ID);
        tableroPrueba.setName("Tablero de prueba");
        tableroPrueba.setRonda(1);
        tableroPrueba.setJugadores(new ArrayList<>());
        tableroPrueba.setMazos(new ArrayList<>());
        given(this.taService.findById(1)).willReturn(tableroPrueba);
    }

    @WithMockUser(value = "spring")
    @Test
    void testShowTablero() throws Exception {
        mockMvc.perform(get("/partida/")).andExpect(status().isOk())
                .andExpect(model().attributeExists("tablero"))
                .andExpect(view().name("tablero/tablero"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessTablero() throws Exception{
        mockMvc.perform(post("/partida/").with(csrf())
                .param("name", "Tablero de prueba"))
                .andExpect(status().is3xxRedirection());
                // NULL???   .andExpect(view().name("redirect:/partida/" + TEST_TABLERO_ID + "/comienza"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testShowTablero1() throws Exception {
        given(this.taService.findById(TEST_TABLERO_ID)).willReturn(tableroPrueba);
        mockMvc.perform(get("/partida/{partidaId}", TEST_TABLERO_ID)).andExpect(status().isOk());
                //.andExpect(view().name("tablero/Showtablerocopy"));
                
    }

    @WithMockUser(value = "spring")
    @Test
    void testRondaPrincipio() throws Exception {
        mockMvc.perform(get("/partida/{partidaId}/comienza", TEST_TABLERO_ID)).andExpect(status().isOk());
                //.andExpect(view().name("redirect:/partida/" + TEST_TABLERO_ID));
    }

    @WithMockUser(value = "spring")
    @Test
    void testRondaColoca() throws Exception {

    }

    @WithMockUser(value = "spring")
    @Test
    void testRondaRecursos() throws Exception {

    }

    @WithMockUser(value = "spring")
    @Test
    void testFinPartida() throws Exception {

    }

    @WithMockUser(value = "spring")
    @Test
    void testBorrarPartida() throws Exception {

    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessChatLine() throws Exception {
        
    }


}
