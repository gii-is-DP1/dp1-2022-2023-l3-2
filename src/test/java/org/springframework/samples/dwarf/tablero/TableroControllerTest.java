package org.springframework.samples.dwarf.tablero;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dwarf.carta.Carta;
import org.springframework.samples.dwarf.carta.CartaService;
import org.springframework.samples.dwarf.carta.TipoCarta;
import org.springframework.samples.dwarf.carta.TipoCartaService;
import org.springframework.samples.dwarf.configuration.SecurityConfiguration;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.user.Authorities;
import org.springframework.samples.dwarf.user.AuthoritiesService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers = TableroController.class, 
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class TableroControllerTest {
    
    private static final int TEST_TABLERO_ID = 1;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TableroService taService;
    @MockBean
    private MazoService mazoService;
    @MockBean
    private CartaService cartaService;

    @MockBean
    private JugadorService jugadorService;

    @MockBean
    private ChatLineService chatLineService;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthoritiesService authoritiesService;

    @MockBean
    private TipoCartaService tipoCartaService;

    private Tablero tableroPrueba;

    /**
     * 
     */
    @BeforeEach
    void setup() {
        tableroPrueba = new Tablero();
        tableroPrueba.setId(1);
        tableroPrueba.setName("Tablero de prueba");
        tableroPrueba.setRonda(1);
        List<Jugador> jugadores = new ArrayList<>();
        Jugador alegarsan = new Jugador();
        alegarsan.setId(1);

        alegarsan.setAcero(0);
        Enano e = new Enano();
        e.setId(1);
        e.setPosicion(12);
        Enano e2 = new Enano();
        e2.setId(2);
        e2.setPosicion(12);
        alegarsan.setEnano(List.of(e, e2));
        alegarsan.setEnanosDisponibles(2);
        alegarsan.setEsespectador(false);
        alegarsan.setHierro(0);
        alegarsan.setMedalla(0);
        alegarsan.setObjeto(0);

        alegarsan.setOro(0);
        alegarsan.setPrimerjugador(true);
        alegarsan.setTurno(true);
        org.springframework.samples.dwarf.user.User ale = new User();
        ale.setUsername("alegarsan11");
        ale.setPassword("fffff");
        userService.saveUser(ale);
        Authorities authority = new Authorities();
        authority.setAuthority("jugador");
        authority.setUser(ale);
        authoritiesService.saveAuthorities(authority);

        alegarsan.setUser(ale);

        Jugador rafgargal = new Jugador();
        rafgargal.setId(2);

        rafgargal.setAcero(0);
        Enano e3 = new Enano();
        e3.setId(3);
        e3.setPosicion(12);
        Enano e4 = new Enano();
        e4.setId(4);
        e4.setPosicion(12);
        rafgargal.setEnano(List.of(e3, e4));
        rafgargal.setEnanosDisponibles(2);
        rafgargal.setEsespectador(false);
        rafgargal.setHierro(0);
        rafgargal.setMedalla(0);
        rafgargal.setObjeto(0);

        rafgargal.setOro(0);
        rafgargal.setPrimerjugador(false);
        rafgargal.setTurno(false);
        org.springframework.samples.dwarf.user.User rafa = new User();
        rafa.setUsername("rafgargal");
        rafa.setPassword("jajajajajaj");
        userService.saveUser(rafa);
        Authorities authorityAuthorities = new Authorities();
        authorityAuthorities.setAuthority("jugador");
        authorityAuthorities.setUser(rafa);
        authoritiesService.saveAuthorities(authority);


        rafgargal.setUser(rafa);
        jugadorService.saveJugador(rafgargal);
        given(jugadorService.findOwnerById(1)).willReturn(alegarsan);
        given(jugadorService.findOwnerById(2)).willReturn(rafgargal);

        jugadores.add(jugadorService.findOwnerById(1));
        jugadores.add(jugadorService.findOwnerById(2));
        tableroPrueba.setJugadores(jugadores);


        Carta cartaPruebas = new Carta();
        cartaPruebas.setId(1);
        cartaPruebas.setCantidaddevuelve(3);
        cartaPruebas.setCantidadentrada(0);
        cartaPruebas.setDevuelve("hierro");
        cartaPruebas.setEntrada("");
        cartaPruebas.setImagen("");
        TipoCarta tipo = new TipoCarta();
        tipo.setId(1);
        tipo.setName("extraccion");
        tipoCartaService.saveTipoCarta(tipo);
        cartaPruebas.setTipo(tipo);
        cartaPruebas.setPosicion(1);
        cartaService.saveCarta(cartaPruebas);

        given(taService.findCartaById(anyInt())).willReturn(cartaPruebas);
        List<Mazo> mazos = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            if (i <= 9) {
                Carta carta = taService.findCartaById(67);
                List<Carta> cartas = new ArrayList<>();
                Mazo mazo = new Mazo();
                mazo.setId(i);
                cartas.add(carta);
                mazo.setPosicion(i);
                mazo.setCartas(cartas);
                mazo.setName("mazo");
                mazoService.saveMazo(mazo);
                mazos.add(mazo);

            } else if (i < 13) {
                Carta carta = taService.findCartaById(67);
                List<Carta> cartas = new ArrayList<>();
                Mazo mazo = new Mazo();
                cartas.add(carta);
                mazo.setCartas(cartas);
                mazo.setPosicion(i);
                mazo.setName("mazo");
                mazoService.saveMazo(mazo);
                mazos.add(mazo);

            } else {
                Mazo mazo = new Mazo();
                List<Carta> cartas = new ArrayList<>();
                for (int j = 10; j < 55; j++) {
                    Carta carta = new Carta();
                    carta = taService.findCartaById(67);
                    cartas.add(carta);
                }

                mazo.setPosicion(i);
                mazo.setCartas(cartas);
                mazo.setName("mazo");
                mazoService.saveMazo(mazo);
                mazos.add(mazo);

            }
        }

        tableroPrueba.setMazos(mazos);
        tableroPrueba.setRonda(1);

        ChatLine chat = new ChatLine();
        chat.setId(1);
        chat.setMensaje("Hol hola");
        chat.setUsername("alegarsan");
        chatLineService.saveChatLine(chat);
        tableroPrueba.setChat(List.of(chat));

        tableroPrueba.setTerminada(false);
        tableroPrueba.setDefensaTotal(false);
        tableroPrueba.setCreatedAt(new Date());
        tableroPrueba.setFinishedAt(new Date());
        System.out.println(tableroPrueba.getMazos().stream().map(j -> j.getCartas()).toList());
        taService.saveTablero(tableroPrueba);
        given(this.taService.findById(1)).willReturn(tableroPrueba);

    }

    @WithMockUser(value = "spring")
    @Test
    void testShowTablero() throws Exception {
        mockMvc.perform(get("/partida/")).andExpect(status().isOk())
                .andExpect(model().attributeExists("tablero"))
                .andExpect(view().name("tablero/tablero"));
    }

    /*
     * @WithMockUser(value = "spring")
     * Test de creacion no funciona porque es automatico ya tenemos creada la
     * partida en el Setup
     * 
     * @Test
     * void testProcessTablero() throws Exception{
     * mockMvc.perform(post("/partida/").with(csrf())
     * .param("name", "Tablero de prueba"))
     * .andExpect(status().is3xxRedirection());
     * // NULL??? .andExpect(view().name("redirect:/partida/" + TEST_TABLERO_ID +
     * "/comienza"));
     * }
     */

    @WithMockUser(value = "spring")
    @Test
    void testShowTablero1() throws Exception {
        given(this.taService.findById(TEST_TABLERO_ID)).willReturn(tableroPrueba);
        mockMvc.perform(get("/partida/{partidaId}", TEST_TABLERO_ID)).andExpect(status().isOk())
                .andExpect(view().name("tablero/Showtablerocopy"));
                
    }

    @WithMockUser(value = "spring")
    @Test
    void testRondaPrincipio() throws Exception {
        mockMvc.perform(get("/partida/{partidaId}/comienza", TEST_TABLERO_ID)).andExpect(status().is(302))
                .andExpect(view().name("redirect:/partida/" + TEST_TABLERO_ID));
    }

    @WithMockUser(value = "spring")
    @Test
    void testRondaColoca() throws Exception {
        mockMvc.perform(get("/partida/{partidaId}/coloca?username=alegarsan11&posicion=1", TEST_TABLERO_ID))
                .andExpect(status().is(302)).andExpect(view().name("redirect:/partida/1"));
        mockMvc.perform(get("/partida/{partidaId}/coloca?username=rafgargal&posicion=11", TEST_TABLERO_ID))
                .andExpect(status().is(302)).andExpect(view().name("redirect:/partida/1"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testRondaRecursos() throws Exception {

        mockMvc.perform(get("/partida/{partidaId}/recursos", TEST_TABLERO_ID))
                .andExpect(status().is(302)).andExpect(view().name("redirect:/partida/1/comienza"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testFinPartida() throws Exception {
        mockMvc.perform(get("/partida/{partidaId}/fin", TEST_TABLERO_ID)).andExpect(status().is(302))
                .andExpect(view().name("redirect:/partida/1"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testBorrarPartida() throws Exception {

    }


    @WithMockUser(value = "spring")
    @Test
    void testShowAllAndEnCurso() throws Exception {
        mockMvc.perform(get("/partida/all")).andExpect(status().is(200))
                .andExpect(view().name("tablero/showListaPartidas"));
        mockMvc.perform(get("/partida/en-curso")).andExpect(status().is(200))
                .andExpect(view().name("tablero/showListaPartidas"));

    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessChatLine() throws Exception {
        mockMvc.perform(get("/partida/1/eleccion-material?material=oro&username=alegarsan11"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/partida/1"));
    }



}
