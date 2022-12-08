package org.springframework.samples.dwarf.tablero;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.dwarf.carta.Carta;

import java.util.List;

import org.springframework.dao.DataAccessException;

@org.springframework.stereotype.Repository
public interface TableroRepository extends Repository<Tablero, String> {

    void save(Tablero tabla) throws DataAccessException;

    Tablero findById(Integer id);

    @Query("SELECT DISTINCT carta FROM Carta carta")
    List<Carta> findAllCartas();

    @Query("SELECT carta FROM Carta carta WHERE carta.id=:id")
    Carta findCartaById(Integer id);

    @Query("SELECT carta FROM Carta carta WHERE carta.posicion=:posicion")
    List<Carta> findByPosicion(Integer posicion);

    void deleteById(Integer id);
}
