package org.springframework.samples.dwarf.lobby;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dwarf.user.Authorities;
import org.springframework.samples.dwarf.user.AuthoritiesService;
import org.springframework.samples.dwarf.user.Estadistica;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class InvitacionJuegoServiceTest {
    
    @Autowired
    protected InvitacionJuegoService invitacionJuegoService;

    @Autowired
    protected LobbyService lobbyService;


    @Autowired
    protected UserService userService;

    @Autowired
    protected AuthoritiesService authoritiesService;

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
        List<User> users = List.of(user1, user2);
        lobby.setUsuarios(users);
        lobby.setNumUsuarios(2);
        lobby.setAdmin("user1");
        lobbyService.saveLobby(lobby);
        InvitacionJuego inv = new InvitacionJuego();
        inv.setUserenvia(user1);
        inv.setUserrecibe(user2);
        inv.setLobbyId(1);
        inv.setId(1);
        invitacionJuegoService.saveInvitacionAmistad(inv);
    }

    @Test
    public void shouldFindFriendsUser() {

        List<String> friends = invitacionJuegoService.findFriendsUser(user1);
        assertThat(friends.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindBoth() {
        List<InvitacionJuego> invs = invitacionJuegoService.findBoth(user1, user2);
        assertThat(invs.size()).isEqualTo(1);
    }
}
