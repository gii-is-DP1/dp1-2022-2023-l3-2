package org.springframework.samples.dwarf.carta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoCartaService {

    private TipoCartaRepository tipoCartaRepository;

    @Autowired
    public TipoCartaService(TipoCartaRepository tipoCartaRepository) {
        this.tipoCartaRepository = tipoCartaRepository;
    }

    public void saveTipoCarta(TipoCarta tipoCarta) {
        tipoCartaRepository.save(tipoCarta);
    }

}
