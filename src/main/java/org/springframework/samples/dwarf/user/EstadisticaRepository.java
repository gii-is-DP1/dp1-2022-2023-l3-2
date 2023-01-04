package org.springframework.samples.dwarf.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadisticaRepository extends CrudRepository<Estadistica, Integer> {

}
