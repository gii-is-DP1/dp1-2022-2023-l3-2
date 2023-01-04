package org.springframework.samples.dwarf.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadisticaService {

    private EstadisticaRepository estatsrepo;

    @Autowired
    public EstadisticaService(EstadisticaRepository estatsrepo) {
        this.estatsrepo = estatsrepo;
    }

    @Transactional
    public void saveEstadistica(Estadistica estadistica) throws DataAccessException {
        estatsrepo.save(estadistica);
    }
}
