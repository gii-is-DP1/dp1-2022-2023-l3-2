package org.springframework.samples.dwarf.jugador;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dwarf.jugador.JugadorRepository;

@org.springframework.stereotype.Repository
public interface JugadorRepository extends Repository<Jugador, Integer> {

    void save(Jugador jugador) throws DataAccessException;

    void delete(Jugador jugador);

    public Collection<Jugador> findAll();

    public Jugador findById(@Param("id") int id);

    public List<Jugador> findByUserUsername(String name);

}
