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
package org.springframework.samples.dwarf.jugador;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dwarf.user.AuthoritiesService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
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
        // creating jugador
        jugadorRepository.save(jugador);
        // creating user
        // userService.saveUser(owner.getUser());
        // creating authorities
        // authoritiesService.saveAuthorities(owner.getUser().getUsername(), "owner");
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

}
