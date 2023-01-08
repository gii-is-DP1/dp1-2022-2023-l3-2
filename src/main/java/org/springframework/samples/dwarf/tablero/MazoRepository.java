package org.springframework.samples.dwarf.tablero;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MazoRepository extends CrudRepository<Mazo, Integer> {
    List<Mazo> findAll();
}
