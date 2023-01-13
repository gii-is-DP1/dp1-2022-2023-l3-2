package org.springframework.samples.dwarf.jugador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dwarf.user.AuthoritiesService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JugadorService {

    private JugadorRepository jugadorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional(readOnly = true)
    public Jugador findJugadorById(int id) throws DataAccessException {
        return jugadorRepository.findById(id);
    }

    @Transactional
    public List<Jugador> findAll() {
        return (List<Jugador>) jugadorRepository.findAll();
    }

    @Transactional
    public List<Jugador> findJugadorUser(String name) {
        return jugadorRepository.findByUserUsername(name);
    }

    @Transactional
    public void saveJugador(Jugador jugador) throws DataAccessException {
        jugadorRepository.save(jugador);

    }

    @Transactional
    public Jugador createJugadorByUsername(String username, Boolean primerJugador) throws DataAccessException {
        User user = userService.findUserByString(username).get(0);

        Jugador jugador = Jugador.crearJugadorInicial(primerJugador);
        jugador.setUser(user);

        jugadorRepository.save(jugador);

        return jugador;
    }

    @Transactional
    public void deleteJugador(Jugador j) {
        jugadorRepository.delete(j);
    }

    @Transactional
    public List<Jugador> creacionLista(String username1, String username2, String username3) {
        List<Jugador> jugadores = new ArrayList<>();
        if (username1 != null) {
            Jugador j = createJugadorByUsername(username1, true);
            jugadores.add(j);
        }
        if (username2 != null) {
            Jugador j = createJugadorByUsername(username2, false);
            jugadores.add(j);
        }
        if (username3 != null) {
            Jugador j = createJugadorByUsername(username3, false);
            jugadores.add(j);
        }
        return jugadores;
    }
}
