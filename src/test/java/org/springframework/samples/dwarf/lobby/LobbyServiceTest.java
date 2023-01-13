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
import org.springframework.samples.dwarf.user.Authorities;
import org.springframework.samples.dwarf.user.AuthoritiesService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LobbyServiceTest {

    @Autowired
    LobbyService lobbyService;

    @Autowired
    UserService userService;

    @Autowired
    AuthoritiesService authoritiesService;

    /*
     * @Test
     *
     * @Transactional
     * public void añadirAmigo() {
     * org.springframework.samples.dwarf.user.User ale = new User();
     * ale.setUsername("alegarsan11");
     * ale.setPassword("fffff");
     * userService.saveUser(ale);
     * Authorities authority = new Authorities();
     * authority.setAuthority("jugador");
     * authority.setUser(ale);
     * authoritiesService.saveAuthorities(authority);
     *
     * org.springframework.samples.dwarf.user.User rafa = new User();
     * rafa.setUsername("rafgargal");
     * rafa.setPassword("fffff");
     * userService.saveUser(rafa);
     * authority.setUser(rafa);
     * authoritiesService.saveAuthorities(authority);
     *
     * Lobby lobby = new Lobby();
     * lobby.setId(1);
     * lobby.setName("lobby prueba");
     * List<User> users = List.of(ale);
     * lobby.setUsuarios(users);
     * lobby.setNumUsuarios(1);
     * lobby.setAdmin("alegarsan11");
     *
     * this.lobbyService.añadirAmigo(lobby, userService.findUser("rafgargal").get(),
     * lobby.getUsuarios().get(0));
     * lobbyService.saveLobby(lobby);
     * assertThat(lobby.getUsuarios().stream().map(u -> u.getUsername()).toList())
     *
     * .isEqualTo(List.of("alegarsan11", "rafgargal"));
     * }
     *
     */
    /*
     * @Test
     *
     * @Transactional
     * public void shouldDeleteById() {
     * org.springframework.samples.dwarf.user.User ale = new User();
     * ale.setUsername("alegarsan11");
     * ale.setPassword("fffff");
     * userService.saveUser(ale);
     * Authorities authority = new Authorities();
     * authority.setAuthority("jugador");
     * authority.setUser(ale);
     * authoritiesService.saveAuthorities(authority);
     *
     * org.springframework.samples.dwarf.user.User rafa = new User();
     * rafa.setUsername("rafgargal");
     * rafa.setPassword("fffff");
     * userService.saveUser(rafa);
     * authority.setUser(rafa);
     * authoritiesService.saveAuthorities(authority);
     *
     * Lobby lobby = new Lobby();
     * lobby.setId(1);
     * lobby.setName("lobby prueba");
     * List<User> users = List.of(ale);
     * lobby.setUsuarios(users);
     * lobby.setNumUsuarios(1);
     * lobby.setAdmin("alegarsan11");
     * lobbyService.saveLobby(lobby);
     * lobby = lobbyService.findById(1);
     * assertThat(lobbyService.findAll().size()).isEqualTo(1);
     * lobbyService.deleteById(1);
     * assertThat(lobbyService.findAll().size()).isEqualTo(0);
     * }
     */
    // @Test
    // @Transactional
    // public void shouldeliminar() {
    // org.springframework.samples.dwarf.user.User ale = new User();
    // ale.setUsername("alegarsan11");
    // ale.setPassword("fffff");
    // userService.saveUser(ale);
    // Authorities authority = new Authorities();
    // authority.setAuthority("jugador");
    // authority.setUser(ale);
    // authoritiesService.saveAuthorities(authority);

    // org.springframework.samples.dwarf.user.User rafa = new User();
    // rafa.setUsername("rafgargal");
    // rafa.setPassword("fffff");
    // userService.saveUser(rafa);
    // authority.setUser(rafa);
    // authoritiesService.saveAuthorities(authority);

    // Lobby lobby = new Lobby();
    // lobby.setId(1);
    // lobby.setName("lobby prueba");
    // List<User> users = List.of(ale);
    // lobby.setUsuarios(users);
    // lobby.setNumUsuarios(1);
    // lobby.setAdmin("alegarsan11");
    // lobbyService.saveLobby(lobby);
    // lobby = lobbyService.findById(1);
    // assertThat(lobbyService.findAll().size()).isEqualTo(1);
    // lobbyService.eliminarLobby(lobby);
    // assertThat(lobbyService.findAll().size()).isEqualTo(0);
    // }

    /*
     * @Test
     * public void shouldFindAll() {
     * List<Lobby> lobbys = lobbyService.findAll();
     *
     * Lobby lobby1 = lobbys.get(0);
     * assertThat(lobby1.getAdmin()).isEqualTo("user1");
     * assertThat(lobby1.getName()).isEqualTo("lobby prueba");
     * assertThat(lobby1.getNumUsuarios()).isEqualTo(1);
     *
     * User ale = userService.findUser("alegarsan11").get();
     * List<User> users = List.of(ale);
     * assertThat(lobby1.getUsuarios()).isEqualTo(users);
     * }
     */

    /*
     * @Test
     * public void shouldFindByUser() {
     * User ale = userService.findUser("alegarsan11").get();
     * List<Lobby> lobbys = lobbyService.findByUser(ale);
     *
     * Lobby lobby1 = lobbys.get(0);
     * assertThat(lobby1.getAdmin()).isEqualTo("user1");
     * assertThat(lobby1.getName()).isEqualTo("lobby prueba");
     * assertThat(lobby1.getNumUsuarios()).isEqualTo(1);
     *
     * List<User> users = List.of(ale);
     * assertThat(lobby1.getUsuarios()).isEqualTo(users);
     * }
     */

}
