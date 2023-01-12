package org.springframework.samples.dwarf.tablero;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.dwarf.carta.Carta;
import org.springframework.samples.dwarf.user.User;

import java.util.List;

import org.springframework.dao.DataAccessException;

@org.springframework.stereotype.Repository
public interface TableroRepository extends Repository<Tablero, String> {

    void save(Tablero tabla) throws DataAccessException;

    Tablero findById(Integer id);

    List<Tablero> findAll();

    @Query("SELECT tablero FROM Tablero tablero WHERE tablero.finishedAt IS NOT NULL")
    List<Tablero> findAllFinished();

    @Query("SELECT tablero FROM Tablero tablero WHERE tablero.finishedAt IS NULL")
    List<Tablero> findAllNotFinished();

    @Query("SELECT DISTINCT carta FROM Carta carta")
    List<Carta> findAllCartas();

    @Query("SELECT carta FROM Carta carta WHERE carta.id=:id")
    Carta findCartaById(Integer id);

    @Query("SELECT carta FROM Carta carta WHERE carta.posicion=:posicion")
    List<Carta> findByPosicion(Integer posicion);

    // List<Tablero> findLastNGamesByUser(User user);

    @Query("SELECT tablero FROM Tablero tablero ORDER BY tablero.finishedAt DESC")
    List<Tablero> findLastGames();

    void deleteById(Integer id);
}
