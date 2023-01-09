package org.springframework.samples.dwarf.lobby;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dwarf.configuration.SecurityConfiguration;
import org.springframework.samples.dwarf.logro.LogroController;
import org.springframework.samples.dwarf.user.Authorities;
import org.springframework.samples.dwarf.user.AuthoritiesService;
import org.springframework.samples.dwarf.user.InvitacionAmistad;
import org.springframework.samples.dwarf.user.InvitacionAmistadService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import java.util.List;

@WebMvcTest(controllers = LobbyController.class, excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class LobbyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LobbyService lobbyService;

    @MockBean
    UserService userService;

    @MockBean
    AuthoritiesService authoritiesService;

    @MockBean
    InvitacionJuegoService invitacionJuegoService;
    @MockBean
    InvitacionAmistadService invitacionAmistadService;

    private User user1;

    private User user2;

    @BeforeEach
    void setup() {
        user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("1234");
        user1.setImgperfil("imagen");

        user1.setEnabled(true);
        userService.saveUser(user1);
        Authorities authorities1 = new Authorities();
        authorities1.setId(1);
        authorities1.setAuthority("jugador");
        authorities1.setUser(user1);
        authoritiesService.saveAuthorities(authorities1);
        user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("1234");
        user2.setImgperfil("imagen");

        user2.setEnabled(true);
        userService.saveUser(user2);
        Authorities authorities2 = new Authorities();
        authorities2.setId(2);
        authorities2.setAuthority("jugador");
        authorities2.setUser(user2);
        authoritiesService.saveAuthorities(authorities2);

        Lobby lobby = new Lobby();
        lobby.setId(1);
        lobby.setName("lobby prueba");
        List<User> users = List.of(user1);
        lobby.setUsuarios(users);
        lobby.setNumUsuarios(1);
        lobby.setAdmin("user1");
        lobbyService.saveLobby(lobby);
        InvitacionJuego inv = new InvitacionJuego();
        inv.setUserenvia(user1);
        inv.setUserrecibe(user2);
        inv.setLobbyId(1);
        inv.setId(1);
        invitacionJuegoService.saveInvitacionAmistad(inv);
        given(lobbyService.findById(1)).willReturn(lobby);
        InvitacionAmistad amigos = new InvitacionAmistad();
        amigos.setId(1);
        amigos.setUserenvia(user1);
        amigos.setUserrecibe(user2);
        System.out.println(user2);
        given(userService.findUserByString("user2")).willReturn(List.of(user2));

    }

    @WithMockUser(value = "spring")
    @Test
    void testShowLobbyForm() throws Exception {
        mockMvc.perform(get("/lobby/")).andExpect(status().isOk())
                .andExpect(model().attributeExists("lobby"))
                .andExpect(view().name("lobby/lobbyForm"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testShow() throws Exception {
        mockMvc.perform(get("/lobby/1")).andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("lobby/showLobby"));
    }

}
