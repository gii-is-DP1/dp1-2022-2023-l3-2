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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import java.util.List;
import java.util.Optional;

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



    @BeforeEach
    void setup() {

        org.springframework.samples.dwarf.user.User ale = new User();
        ale.setUsername("alegarsan11");
        ale.setPassword("fffff");
        userService.saveUser(ale);
        Authorities authority = new Authorities();
        authority.setAuthority("jugador");
        authority.setUser(ale);
        authoritiesService.saveAuthorities(authority);

        org.springframework.samples.dwarf.user.User rafa = new User();
        rafa.setUsername("rafgargal");
        rafa.setPassword("fffff");
        userService.saveUser(rafa);
        authority.setUser(rafa);
        authoritiesService.saveAuthorities(authority);

        Lobby lobby = new Lobby();
        lobby.setId(1);
        lobby.setName("lobby prueba");
        List<User> users = List.of(ale);
        lobby.setUsuarios(users);
        lobby.setNumUsuarios(1);
        lobby.setAdmin("user1");
        lobbyService.saveLobby(lobby);
        InvitacionJuego inv = new InvitacionJuego();
        inv.setUserenvia(ale);
        inv.setUserrecibe(rafa);
        inv.setLobbyId(1);
        inv.setId(1);
        invitacionJuegoService.saveInvitacionAmistad(inv);
        given(lobbyService.findById(1)).willReturn(lobby);
        InvitacionAmistad amigos = new InvitacionAmistad();
        amigos.setId(1);
        amigos.setUserenvia(ale);
        amigos.setUserrecibe(rafa);
        invitacionAmistadService.saveInvitacionAmistad(amigos);
        Optional<User> aOptional = Optional.of(rafa);
        given(this.userService.findUser("rafgargal")).willReturn(aOptional);

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

    @WithMockUser(value = "spring")
    @Test
    void testAddUser() throws Exception {
        mockMvc.perform(post("/lobby/1/add-user").with(csrf())).andExpect(status().is(302))
                .andExpect(view().name("redirect:/lobby/1"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testAddUser2() throws Exception {
        mockMvc.perform(get("/lobby/1/add-user?exactUsername=user2")).andExpect(status().is(302))
                .andExpect(view().name("redirect:/lobby/1"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testDelUser() throws Exception {
        mockMvc.perform(get("/lobby/1/add-user?exactUsername=user2")).andExpect(status().is(302))
                .andExpect(view().name("redirect:/lobby/1"));
        mockMvc.perform(get("/lobby/1/delete-user?username=user2")).andExpect(status().is(302))
                .andExpect(view().name("redirect:/lobby/1"));

    }
}
