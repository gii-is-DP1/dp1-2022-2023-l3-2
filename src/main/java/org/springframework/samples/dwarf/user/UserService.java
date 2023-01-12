/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.dwarf.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.lobby.InvitacionJuegoService;
import org.springframework.samples.dwarf.lobby.LobbyService;
import org.springframework.samples.dwarf.tablero.TableroService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private JugadorService jService;
    private InvitacionAmistadService invitacionAmistadService;
    private InvitacionJuegoService invitacionJuegoService;
    private LobbyService lobbyService;
    private TableroService taservice;

    @Autowired
    public UserService(UserRepository userRepository, JugadorService jService,
            InvitacionAmistadService invitacionAmistadService, LobbyService lobbyService,
            TableroService taservice,
            InvitacionJuegoService invitacionJuegoService) {
        this.userRepository = userRepository;
        this.jService = jService;
        this.invitacionAmistadService = invitacionAmistadService;
        this.lobbyService = lobbyService;
        this.taservice = taservice;
        this.invitacionJuegoService = invitacionJuegoService;
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
    public void deleteUser(User user) {
        invitacionAmistadService.findInvitacionesByUser(user).stream()
                .forEach(invi -> invitacionAmistadService.deleteInvitacionAmistad(invi));

        invitacionJuegoService.findInvitacionesByUser(user).stream()
                .forEach(invi -> invitacionJuegoService.delInvi(invi));

        lobbyService.findByUser(user).stream().forEach(lobby -> {
            lobby.getUsuarios().clear();
            if (lobby.getAdmin().equals(user.getUsername())) {
                lobbyService.deleteById(lobby.getId());
            } else {
                lobby.getUsuarios().remove(user);
                lobby.setNumUsuarios(lobby.getNumUsuarios() - 1);
            }
        });

        taservice.findByUser(user).stream().forEach(tab -> {
            tab.getJugadores().clear();
            taservice.deleteById(tab.getId());
        });

        jService.findJugadorUser(user.getUsername()).stream().forEach(j -> jService.deleteJugador(j));

        userRepository.delete(user);

    }

    @Transactional
    public void deleteUserById(String username) {
        deleteUser(findUser(username).get());
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

    // Hay que hacerlo con QUERY
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
            for (Jugador j : jService.findJugadorUser(u.getUsername())) {
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
