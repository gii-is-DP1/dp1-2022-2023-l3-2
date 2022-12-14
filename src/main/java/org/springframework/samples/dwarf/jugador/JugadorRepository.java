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

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dwarf.jugador.JugadorRepository;
import org.springframework.samples.dwarf.model.BaseEntity;

/**
 * Spring Data JPA OwnerRepository interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
@org.springframework.stereotype.Repository
public interface JugadorRepository extends Repository<Jugador, Integer> {

    /**
     * Save an <code>Owner</code> to the data store, either inserting or updating
     * it.
     *
     * @param owner the <code>Owner</code> to save
     * @see BaseEntity#isNew
     */
    void save(Jugador owner) throws DataAccessException;

    void delete(Jugador jugador);

    /**
     * Retrieve <code>Owner</code>s from the data store by last name, returning all
     * owners
     * whose last name <i>starts</i> with the given name.
     *
     * @param lastName Value to search for
     * @return a <code>Collection</code> of matching <code>Owner</code>s (or an
     *         empty
     *         <code>Collection</code> if none found)
     */

    public Collection<Jugador> findAll();

    /**
     * Retrieve an <code>Owner</code> from the data store by id.
     *
     * @param id the id to search for
     * @return the <code>Owner</code> if found
     * @throws org.springframework.dao.DataRetrievalFailureException if not found
     */

    public Jugador findById(@Param("id") int id);

    public List<Jugador> findByUserUsername(String name);

    // public void createJugadorByUsername(String username, Boolean primerJugador)
    // throws DataAccessException;

}
