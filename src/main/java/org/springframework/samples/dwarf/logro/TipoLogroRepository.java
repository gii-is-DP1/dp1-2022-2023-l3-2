package org.springframework.samples.dwarf.logro;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TipoLogroRepository extends CrudRepository<TipoLogro, Integer> {

    List<TipoLogro> findAll();
}
