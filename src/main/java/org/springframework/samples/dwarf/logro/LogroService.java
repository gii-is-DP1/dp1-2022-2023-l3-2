package org.springframework.samples.dwarf.logro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class LogroService {
    private LogroRepository logroRepository;
    private TipoLogroRepository tipoLogroRepository;
    private UserService userService;
    private JugadorService jugadorService;

    @Autowired
    public LogroService(LogroRepository logroRepository, TipoLogroRepository tipoLogroRepository,
            UserService userService, JugadorService jugadorService) {
        this.logroRepository = logroRepository;
        this.tipoLogroRepository = tipoLogroRepository;
        this.userService = userService;
        this.jugadorService = jugadorService;
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

    public List<Logro> findLogrosByUsername(String username) {

        User usuario = userService.findUser(username).get();

        List<Jugador> jugadores = jugadorService.findJugadorUser(username);

        List<Logro> logrosAll = findAll();
        List<Logro> logrosCumplidos = new ArrayList<>();

        for (Logro logro : logrosAll) {
            if (logro.getTipo().getName().equals("partidas_ganadas")) {
                if (usuario.getEstadistica().getPartidasGanadas() >= logro.getRequisito())
                    logrosCumplidos.add(logro);
            }
            if (logro.getTipo().getName().equals("recursos_conseguidos")) {

                Integer hierro = 0;
                Integer acero = 0;
                Integer objetos = 0;
                Integer medallas = 0;
                Integer oro = 0;
                for (Jugador j : jugadores) {
                    hierro += j.getHierro();
                    acero += j.getAcero();
                    objetos += j.getObjeto();
                    medallas += j.getMedalla();
                    oro += j.getOro();
                }

                if (logro.getName().contains("hierro") && hierro >= logro.getRequisito())
                    logrosCumplidos.add(logro);
                if (logro.getName().contains("acero") && acero >= logro.getRequisito())
                    logrosCumplidos.add(logro);
                if (logro.getName().contains("objetos") && objetos >= logro.getRequisito())
                    logrosCumplidos.add(logro);
                if (logro.getName().contains("medallas") && medallas >= logro.getRequisito())
                    logrosCumplidos.add(logro);
                if (logro.getName().contains("oro") && oro >= logro.getRequisito())
                    logrosCumplidos.add(logro);
            }

        }

        return logrosCumplidos;
    }
}
