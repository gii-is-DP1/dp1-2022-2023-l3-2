package org.springframework.samples.dwarf.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dwarf.configuration.SecurityConfiguration;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.lobby.InvitacionJuegoService;
import org.springframework.samples.dwarf.lobby.LobbyService;
import org.springframework.samples.dwarf.logro.LogroService;
import org.springframework.samples.dwarf.tablero.TableroService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private JugadorService jugadorService;

    @MockBean
    private UserService userService;

    @MockBean
    private InvitacionJuegoService invitacionJuegoService;

    @MockBean
    private LogroService logroService;

    @MockBean
    private LobbyService lobbyService;

    @MockBean
    private InvitacionAmistadService invitacionAmistadService;

    @MockBean
    private AuthoritiesService authoritiesService;

    @MockBean
    private EstadisticaService estadisticaService;

    @MockBean
    private TableroService tableroService;

    private User user1;

    @BeforeEach
    void setup() {
        user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("1234");
        user1.setEnabled(true);
        user1.setImgperfil("img");

        Authorities authority = new Authorities();
        authority.setUser(user1);
        authority.setAuthority("admin");
        authoritiesService.saveAuthorities(authority);
        Set<Authorities> au = Set.of(authority);

        Estadistica stats = new Estadistica();
        stats.setUsuario(user1);
        stats.setAcero(0);
        stats.setHierro(0);
        stats.setMedallas(0);
        stats.setObjetos(0);
        stats.setOro(0);
        stats.setPartidasGanadas(0);
        stats.setPartidasPerdidas(0);
        stats.setPuntos(0);
        estadisticaService.saveEstadistica(stats);

        user1.setAuthorities(au);
        user1.setEstadistica(stats);
        userService.saveUser(user1);
        given(this.userService.findAuthenticatedUser()).willReturn(user1);
        given(this.userService.getPages(any())).willReturn(List.of(List.of(user1)));
    }

    @WithMockUser(value = "spring")
    @Test
    void testParteDeUsuarios() throws Exception {
        mockMvc.perform(get("/users?page=0")).andExpect(status().isOk())
                .andExpect(model().attributeExists("perfil"))
                .andExpect(model().attributeExists("usuarios"))

                .andExpect(view().name("users/welcomecopy"));
    }

    /*
     * @WithMockUser(value = "spring")
     *
     * @Test
     * void testUsersList() throws Exception {
     * mockMvc.perform(get("/user")).andExpect(status().isOk())
     * .andExpect(model().attributeExists("puntuacion"))
     * .andExpect(model().attributeExists("usuarios"))
     * .andExpect(model().attributeExists("paginaActual"))
     * .andExpect(model().attributeExists("paginas"))
     * .andExpect(view().name("users/usersList"));
     * }
     */

    @WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/user/find")).andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("users/findUsers"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post("/users/find").with(csrf())
                .param("username", "user1")
                .param("password", "1234")
                .param("enabled", "true")
                .param("imgperfil", "img"))
                .andExpect(status().is3xxRedirection());
    }

    /*
     * @WithMockUser(value = "spring")
     *
     * @Test
     * void testProcessCreationFormHasErrors() throws Exception {
     * mockMvc.perform(post("/users/find").with(csrf())
     * .param("username","user1")
     * .param("password","1234")
     * .param("enabled","si")
     * .param("imgperfil","img"))
     * .andExpect(status().is3xxRedirection())
     * .andExpect(model().attributeHasErrors("user"))
     * .andExpect(model().attributeHasFieldErrors("user", "enabled"))
     * .andExpect(view().name("redirect:/user"));
     * }
     */

    @WithMockUser(value = "spring")
    @Test
    void testProcessAddFriendFormSuccess() throws Exception {
        mockMvc.perform(post("/users/friend").with(csrf()))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    void testShowUser() throws Exception {
        mockMvc.perform(get("/users/{userid}", user1.getUsername())).andExpect(status().isOk());
        /*
         * .andExpect(model().attributeExists("usuarios"))
         * .andExpect(model().attributeExists("imagen"))
         * .andExpect(model().attributeExists("user"))
         * .andExpect(model().attributeExists("usuario"))
         * .andExpect(model().attributeExists("jugadores"))
         * .andExpect(model().attributeExists("logros"))
         * .andExpect(model().attributeExists("currentUsername"))
         * .andExpect(model().attributeExists("promedios"))
         * .andExpect(model().attributeExists("partidas"))
         * .andExpect(view().name("users/showUser"));
         */
    }


    @WithMockUser(value = "spring")
    @Test
    void testCreateNewUser() throws Exception {
        mockMvc.perform(get("/usersnew")).andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("users/creatForm"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testCreateNewUser1() throws Exception {
        mockMvc.perform(post("/usersnew").with(csrf())
                .param("username", "user1")
                .param("password", "1234")
                .param("enabled", "true")
                .param("imgperfil", "img"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(value = "alegarsan11")
    @Test
    void testModifyUser() throws Exception {
        mockMvc.perform(get("/users/mod?user=alegarsan11")).andExpect(status().isOk())
                .andExpect(view().name("users/modForm"));
    }
    /*
     * Poner en el service una funcion para hacer boolean de si esta autenticado
     * 
     * @WithMockUser(value = "alegarsan11")
     * 
     * @Test
     * void testModifyUser1() throws Exception {
     * mockMvc.perform(post("/users/mod?user=alegarsan11").with(csrf())
     * .param("password", "9876"))
     * .andExpect(status().is(200))
     * .andExpect(view().name("exception"));
     * }
     */

    @WithMockUser(value = "spring")
    @Test
    void testGetNotFriends() throws Exception {
        mockMvc.perform(get("/users/{userid}/search-friends", user1.getUsername()))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    void testProccessAddFriend() throws Exception {
        mockMvc.perform(get("/users/{userid}/add-friend", user1.getUsername()))
                .andExpect(status().isOk());
    }

}
