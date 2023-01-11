package org.springframework.samples.dwarf.tablero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.carta.Carta;
import org.springframework.samples.dwarf.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.transaction.TransactionScoped;

@Service
public class TableroService {

    TableroRepository repo;

    @Autowired
    public TableroService(TableroRepository repo) {
        this.repo = repo;
    }

    public List<Tablero> findAll() {
        return repo.findAll();
    }

    public List<Tablero> findAllFinished() {
        return repo.findAllFinished();
    }

    public Tablero findById(Integer id) {
        return repo.findById(id);
    }

    public void saveTablero(Tablero tabla) {
        repo.save(tabla);
    }

    public List<Carta> findAllCartas() {
        return repo.findAllCartas();
    }

    public Carta findCartaById(Integer id) {
        return repo.findCartaById(id);
    }

    public List<Carta> findByPosicion(Integer posicion) {
        return repo.findByPosicion(posicion);
    }

    @Transactional
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    public List<Tablero> findByUser(User user) {
        List<Tablero> all = findAll();
        return all.stream().filter(
                tab -> tab.getJugadores().stream().anyMatch(j -> j.getUser().getUsername().equals(user.getUsername())))
                .toList();
    }

    public List<Tablero> findEnCursoByUser(User user) {
        return findByUser(user).stream().filter(tab -> !tab.isTerminada()).toList();
    }

    // NO HECHO CON QUERY
    public List<Tablero> findLastNGamesByUser(User user, Integer n) {
        return repo.findAll().stream()
                .filter(t -> t.getJugadores().stream().anyMatch(j -> j.getUser().equals(user)))
                .filter(t -> t.isTerminada())
                .sorted(Comparator.comparing(Tablero::getFinishedAt).reversed())
                .limit(n)
                .toList();
    }

    public List<Tablero> findLastNGames(Integer n) {
        return repo.findLastGames().stream().filter(tab -> tab.isTerminada()).limit(n).toList();
    }
}
