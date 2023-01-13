package org.springframework.samples.dwarf.logro;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface LogroRepository extends CrudRepository<Logro, Integer> {
    
    List<Logro> findAll();
    
}
