package org.springframework.samples.dwarf.lobby;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.tablero.Tablero;
import org.springframework.samples.dwarf.tablero.TableroService;
import org.springframework.samples.dwarf.user.Authorities;
import org.springframework.samples.dwarf.user.AuthoritiesService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LobbyServiceTest {

    @Autowired
    LobbyService lobbyService;

    @Autowired
    UserService userService;

    @Autowired
    TableroService tableroService;

    @Autowired
    AuthoritiesService authoritiesService;

    @Autowired
    JugadorService jugadorService;

    @Autowired
    InvitacionJuegoService invitacionJuegoService;

    @Test
    public void shouldCondicionCantidadUsuarios() {
        Lobby lobby = new Lobby();
        User user = userService.findUser("alegarsan11").get();
        lobbyService.creacionLobby(lobby, user);
        assertThat(lobbyService.condicionCantidadUsuarios(lobby)).isEqualTo(false);
    }

    @Test
    public void shouldCondicionEstaPresente() {
        Lobby lobby = new Lobby();
        Optional<User> user = userService.findUser("rafgargal");
        lobbyService.creacionLobby(lobby, user.get());
        assertThat(lobbyService.condicionEstaPresente(user)).isEqualTo(false);
    }

    @Test
    public void shouldFindBy() {
        Lobby lobby = new Lobby();
        User user = userService.findUser("alegarsan11").get();
        lobbyService.creacionLobby(lobby, user);
        assertThat(lobbyService.findById(lobby.getId())).isEqualTo(lobby);
    }

    @Test
    public void shouldDeleteById() {
        Lobby lobby1 = new Lobby();
        Lobby lobby2 = new Lobby();
        User user = userService.findUser("alegarsan11").get();
        lobbyService.creacionLobby(lobby1, user);
        lobbyService.creacionLobby(lobby2, user);
        assertThat(lobbyService.findAll().size()).isEqualTo(2);
        lobbyService.deleteById(lobby2.getId());
        assertThat(lobbyService.findAll().size()).isEqualTo(1);
    }

    @Test
    public void shouldFindAll() {
        Lobby lobby = new Lobby();
        User user = userService.findUser("rafgargal").get();
        lobbyService.creacionLobby(lobby, user);
        List<Lobby> lobbys = lobbyService.findAll();
        assertThat(lobbys.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindByUser() {
        Lobby lobby = new Lobby();
        User user = userService.findUser("rafgargal").get();
        lobbyService.creacionLobby(lobby, user);
        List<Lobby> lobbys = lobbyService.findByUser(user);
        assertThat(lobbys.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindByTablero() {
        List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
        Tablero t = tableroService.saveTableroFromProcess("Tablero", "alegarsan11", "rafgargal", null, jugadores);
        Lobby l = new Lobby();
        lobbyService.creacionLobby(l, userService.findUser("alegarsan11").get());
        lobbyService.setTableroId(l, t.getId());
        assertThat(lobbyService.findByTableroId(t.getId()).getAdmin()).isEqualTo(l.getAdmin());

    }

    @Test
    public void shouldEliminarLobby() {
        Lobby l = new Lobby();
        lobbyService.creacionLobby(l, userService.findUser("alegarsan11").get());
        Lobby l2 = new Lobby();
        lobbyService.creacionLobby(l2, userService.findUser("alegarsan11").get());
        assertEquals(lobbyService.findAll().size(), 2);
        lobbyService.eliminarLobby(l);
        assertEquals(lobbyService.findAll().size(), 1);
    }

    @Test
    public void shouldAñadirAmigo() {
        Lobby l = new Lobby();
        lobbyService.creacionLobby(l, userService.findUser("alegarsan11").get());
        lobbyService.añadirAmigo(l, userService.findUser("rafgargal").get(), userService.findUser("alegarsan11").get());
        assertEquals(l.getUsuarios().stream().map(u -> u.getUsername()).toList(), List.of("alegarsan11", "rafgargal"));
    }

    @Test
    public void shouldEliminarAmigo() {
        Lobby l = new Lobby();
        lobbyService.creacionLobby(l, userService.findUser("alegarsan11").get());
        lobbyService.añadirAmigo(l, userService.findUser("rafgargal").get(), userService.findUser("alegarsan11").get());
        lobbyService.eliminarAmigo(l, userService.findUser("rafgargal").get(),
                invitacionJuegoService.findInvitacionesByUser(userService.findUser("alegarsan11").get()));
        assertEquals(l.getUsuarios().stream().map(u -> u.getUsername()).toList(), List.of("alegarsan11"));
    }
}
