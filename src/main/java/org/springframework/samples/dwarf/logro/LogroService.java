package org.springframework.samples.dwarf.logro;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class LogroService {
    private LogroRepository logroRepository;
    private TipoLogroRepository tipoLogroRepository;
    @Autowired
    public LogroService(LogroRepository logroRepository, TipoLogroRepository tipoLogroRepository) {
        this.logroRepository = logroRepository;
        this.tipoLogroRepository = tipoLogroRepository;
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

    @ModelAttribute("tipos")
    public Collection<TipoLogro> tiposDeLogros() {
        return this.tipoLogroRepository.findAll().stream().distinct().toList();
    }
}
