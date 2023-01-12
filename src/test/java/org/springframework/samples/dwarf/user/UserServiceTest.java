package org.springframework.samples.dwarf.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {

    @Autowired
    protected UserService userService;

    @Test
    public void shouldDeleteUser() {
        User dandiasua = userService.findUser("dandiasua").get();
        userService.deleteUser(dandiasua);
        assertEquals(8, userService.findAll().size());
    }

    @Test
    public void shouldDeleteUserById() {
        userService.deleteUserById("dandiasua");
        assertEquals(8, userService.findAll().size());
    }

    @Test
    public void shouldFindAll() {
        List<User> users = userService.findAll();

        User alegarsan11 = users.get(2);
        assertThat(alegarsan11.getUsername()).isEqualTo("alegarsan11");
        assertThat(alegarsan11.getPassword()).isEqualTo("1234");
        assertThat(alegarsan11.getImgperfil()).isEqualTo("https://previews.123rf.com/images/yupiramos/yupiramos1708/yupiramos170831273/84892638-icono-del-avatar-hombre-sobre-ilustraci%C3%B3n-de-vectores-de-fondo-blanco.jpg");
    }

    @Test
    public void shouldFindUser() {
        Optional<User> dandiasua = userService.findUser("dandiasua");
        assertThat(dandiasua.get().getUsername()).isEqualTo("dandiasua");
        assertThat(dandiasua.get().getPassword()).isEqualTo("1234");
    }

    @Test
    public void shouldFindUserByString() {
        List<User> users = userService.findUserByString("jua");

        User jualeomad = users.get(0);
        assertThat(jualeomad.getUsername()).isEqualTo("jualeomad");
        assertThat(jualeomad.getPassword()).isEqualTo("1234");
    }

    @Test
    public void shouldFindByRol() {
        List<User> users = userService.findByRol("jugador");

        User alegarsan11 = users.get(1);
        assertTrue(alegarsan11.hasRole("jugador"));
        assertThat(alegarsan11.getUsername()).isEqualTo("alegarsan11");
        assertThat(alegarsan11.getPassword()).isEqualTo("1234");
        assertThat(alegarsan11.getImgperfil()).isEqualTo("https://previews.123rf.com/images/yupiramos/yupiramos1708/yupiramos170831273/84892638-icono-del-avatar-hombre-sobre-ilustraci%C3%B3n-de-vectores-de-fondo-blanco.jpg");
    }

    @Test
    public void shouldFindJugadoresSortedByPuntuacion() {
        List<User> users = userService.findJugadoresSortedByPuntuacion();

        User jualeomad = users.get(0);
        assertThat(jualeomad.getUsername()).isEqualTo("jualeomad");
        assertThat(jualeomad.getPassword()).isEqualTo("1234");
    }

    @Test
    public void shouldGetPuntuaciones() {
        Map<User, Integer> puntuaciones = userService.getPuntuaciones();

        User jualeomad = userService.findUser("jualeomad").get();
        Integer punt = puntuaciones.get(jualeomad);
        assertThat(punt).isEqualTo(0);
    }

    @Test
    public void shouldGetPages() {
        List<User> usuarios = List.of(userService.findUser("alegarsan11").get());
        userService.getPages(usuarios);
        assertEquals(userService.getPages(usuarios).size(), 1);
    }

}
