package org.springframework.samples.dwarf.tablero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TableroService {

    TableroRepository repo;

    @Autowired
    public TableroService(TableroRepository repo) {
        this.repo = repo;
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
}
