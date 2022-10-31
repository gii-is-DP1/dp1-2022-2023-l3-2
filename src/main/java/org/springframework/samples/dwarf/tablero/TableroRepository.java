package org.springframework.samples.dwarf.tablero;
import org.springframework.data.repository.Repository;
import org.springframework.dao.DataAccessException;


public interface TableroRepository  extends Repository<Tablero, String>{
    
    void save(Tablero tabla) throws DataAccessException;

    Tablero findById(Integer id);
}
