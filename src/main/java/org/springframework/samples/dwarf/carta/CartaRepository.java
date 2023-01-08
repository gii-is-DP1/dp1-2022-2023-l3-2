package org.springframework.samples.dwarf.carta;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CartaRepository extends CrudRepository<Carta, Integer> {
    List<Carta> findAll();
}
