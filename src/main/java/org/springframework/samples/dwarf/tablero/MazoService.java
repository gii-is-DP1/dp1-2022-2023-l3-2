package org.springframework.samples.dwarf.tablero;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MazoService {

    private MazoRepository mazoRepository;

    @Autowired
    public MazoService(MazoRepository mazoRepository) {
        this.mazoRepository = mazoRepository;
    }

    public void saveMazo(Mazo mazo) {
        mazoRepository.save(mazo);
    }

    public List<Mazo> findAll() {
        return mazoRepository.findAll();
    }

}
