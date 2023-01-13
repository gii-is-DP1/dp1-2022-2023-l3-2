package org.springframework.samples.dwarf.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorRepository;
import org.springframework.samples.dwarf.lobby.InvitacioJuegoRepository;
import org.springframework.samples.dwarf.lobby.LobbyRepository;
import org.springframework.samples.dwarf.tablero.Tablero;
import org.springframework.samples.dwarf.tablero.TableroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Service
public class UserService {

    private UserRepository userRepository;
    private InvitacioAmistadRepository invitacioAmistadRepository;
    private InvitacioJuegoRepository invitacioJuegoRepository;
    private LobbyRepository lobbyRepository;
    private TableroRepository tableroRepository;
    private JugadorRepository jugadorRepository;

    @Autowired
    public UserService(UserRepository userRepository, InvitacioAmistadRepository invitacioAmistadRepository,
            InvitacioJuegoRepository invitacioJuegoRepository, LobbyRepository lobbyRepository,
            JugadorRepository jugadorRepository,
            TableroRepository tableroRepository) {
        this.userRepository = userRepository;
        this.invitacioAmistadRepository = invitacioAmistadRepository;
        this.invitacioJuegoRepository = invitacioJuegoRepository;
        this.lobbyRepository = lobbyRepository;
        this.tableroRepository = tableroRepository;
        this.jugadorRepository = jugadorRepository;

    }

    @Transactional
    public void saveUser(User user) throws DataAccessException {
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) authentication
                        .getPrincipal();

                return findUser(currentUser.getUsername()).get();
            }
        }
        return null;

    }

    @Transactional
    public void deleteUser(User user, List<Tablero> tableroPorusuario) {
        invitacioAmistadRepository.findInvitacionesByUser(user).stream()
                .forEach(invi -> invitacioAmistadRepository.delete(invi));

        invitacioJuegoRepository.findInvitacionesByUser(user).stream()
                .forEach(invi -> invitacioJuegoRepository.delete(invi));

        lobbyRepository.findByUser(user).stream().forEach(lobby -> {
            lobby.getUsuarios().clear();
            if (lobby.getAdmin().equals(user.getUsername())) {
                lobbyRepository.deleteById(lobby.getId());
            } else {
                lobby.getUsuarios().remove(user);
                lobby.setNumUsuarios(lobby.getNumUsuarios() - 1);
            }
        });

        tableroPorusuario.stream().forEach(tab -> {
            tab.getJugadores().clear();
            tableroRepository.deleteById(tab.getId());
        });

        jugadorRepository.findByUserUsername(user.getUsername()).stream().forEach(j -> jugadorRepository.delete(j));

        userRepository.delete(user);

    }

    @Transactional
    public void deleteUserById(String username, List<Tablero> tableros) {
        deleteUser(findUser(username).get(), tableros);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        Iterable<User> users = userRepository.findAll();
        List<User> usersList = new ArrayList<>();
        for (User u : users) {
            usersList.add(u);
        }
        return usersList;
    }

    @Transactional(readOnly = true)
    public Optional<User> findUser(String username) {
        return userRepository.findById(username);
    }

    @Transactional(readOnly = true)
    public List<User> findUserByString(String username) {
        return this.findAll().stream().filter(user -> user.getUsername().contains(username) && user.hasRole("jugador"))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<User> findByRol(String rol) {
        return findAll().stream().filter(usr -> usr.hasRole("jugador")).toList();
    }

    @Transactional(readOnly = true)
    public List<User> findJugadoresSortedByPuntuacion() {
        return userRepository.findAllSortedByPuntuacion().stream().filter(usr -> usr.hasRole("jugador")).toList();
    }

    @Transactional(readOnly = true)
    public List<List<User>> getPages(List<User> usuarios) {
        final int PAGE_SIZE = 5;
        int pageNumber = 0;

        if (usuarios.size() % PAGE_SIZE != 0) {
            pageNumber = (usuarios.size() / PAGE_SIZE) + 1;
        } else {
            pageNumber = usuarios.size() / PAGE_SIZE;
        }

        List<List<User>> partition = new ArrayList<>();
        for (int i = 0; i < pageNumber; i++) {
            partition.add(new ArrayList<>());
        }

        int startIndex = 0;
        int finishIndex = PAGE_SIZE;
        for (int i = 0; i < partition.size(); i++) {
            if (finishIndex > usuarios.size()) {
                partition.set(i, usuarios.subList(startIndex, usuarios.size()));
                break;
            }
            partition.set(i, usuarios.subList(startIndex, finishIndex));
            startIndex += PAGE_SIZE;
            finishIndex += PAGE_SIZE;
        }
        return partition;
    }

    @Transactional(readOnly = true)
    public Map<User, Integer> getPuntuaciones() {
        List<User> usuarios = findByRol("jugador");
        List<User> totalUsuarios = new ArrayList<>();
        Map<User, List<Integer>> totalJugadores = new HashMap<>();
        Map<User, Integer> acum = new HashMap<>();
        for (User u : usuarios) {

            totalUsuarios.add(u);

            List<Integer> ls = new ArrayList<>();
            totalJugadores.put(u, ls);
            for (Jugador j : jugadorRepository.findByUserUsername(u.getUsername())) {
                if (j.getPosicionFinal() != null)
                    totalJugadores.get(u).add(j.getPosicionFinal());

            }

            for (int i = 0; i < totalJugadores.size(); i++) {
                List<Integer> aux = totalJugadores.get(u);
                Integer total = 0;
                if (!aux.isEmpty()) {
                    total = aux.stream().reduce((t, b) -> t + b).get();
                }

                acum.put(u, total);
            }
        }
        return acum;
    }
}
