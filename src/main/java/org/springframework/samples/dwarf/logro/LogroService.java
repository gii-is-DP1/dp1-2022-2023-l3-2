package org.springframework.samples.dwarf.logro;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogroService {
    private LogroRepository logroRepository;

    @Autowired
    public LogroService(LogroRepository logroRepository) {
        this.logroRepository = logroRepository;
    }

    @Transactional
    public List<Logro> findAll() {
        return (List<Logro>) logroRepository.findAll();
    }

    @Transactional
    public void save(Logro logro) {
        logroRepository.save(logro);
    }

    @Transactional
    public Logro findById(Integer id) {
        return logroRepository.findById(id).get();
    }

    @Transactional
    public void delLogro(Integer id) {
        logroRepository.delete(findById(id));
    }
}
