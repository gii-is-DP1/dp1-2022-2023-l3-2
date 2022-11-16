package org.springframework.samples.dwarf.logro;

import java.util.List;

import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface LogroRepository extends Repository<Logro, Integer> {
    /*
     * void save(Tablero tabla) throws DataAccessException;
     *
     * Tablero findById(Integer id);
     *
     * @Query("SELECT DISTINCT carta FROM Carta carta")
     * List<Carta> findAllCartas();
     *
     * @Query("SELECT carta FROM Carta carta WHERE carta.id=:id")
     * Carta findCartaById(Integer id);
     *
     * @Query("SELECT carta FROM Carta carta WHERE carta.posicion=:posicion")
     * List<Carta> findByPosicion(Integer posicion);
     *
     * void deleteById(Integer id);
     */

    List<Logro> findAll();
}
