package org.springframework.samples.dwarf.user;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dwarf.lobby.InvitacionJuego;
import org.springframework.samples.dwarf.lobby.InvitacionJuegoService;
import org.springframework.samples.dwarf.lobby.Lobby;
import org.springframework.samples.dwarf.lobby.LobbyService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class InvitacionAmistadServiceTest {
    
    @Autowired
    protected InvitacionAmistadService invitacionAmistadService;

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

        InvitacionAmistad invAmis = new InvitacionAmistad();
        invAmis.setUserenvia(user1);
        invAmis.setUserrecibe(user2);
        invitacionAmistadService.saveInvitacionAmistad(invAmis);
    }

    @Test
    public void shouldFindFriendUser() {
        List<String> friends = invitacionAmistadService.findFriendsUser(user1);
        assertThat(friends.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindFriends() {
        List<InvitacionAmistad> friends = invitacionAmistadService.findFriends(user1);
        assertThat(friends.size()).isEqualTo(1);

        InvitacionAmistad inv = friends.get(0);
        assertThat(inv.getUserenvia()).isEqualTo(user1);
        assertThat(inv.getUserrecibe()).isEqualTo(user2);
    }

    @Test
    public void shouldFindInvitacionesByUser() {
        List<InvitacionAmistad> invs = invitacionAmistadService.findInvitacionesByUser(user1);
        assertThat(invs.size()).isEqualTo(1);
        assertThat(invs.get(0).getUserenvia()).isEqualTo(user1);
    }

    @Test
    public void shouldDeleteInvitacionAmistad() {
        InvitacionAmistad inv = invitacionAmistadService.findInvitacionesByUser(user1).get(0);
        invitacionAmistadService.deleteInvitacionAmistad(inv);
        assertThat(invitacionAmistadService.findInvitacionesByUser(user1).size()).isEqualTo(0);
    }

}
