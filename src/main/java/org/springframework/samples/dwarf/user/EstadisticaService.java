package org.springframework.samples.dwarf.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.tablero.Tablero;
import org.springframework.samples.dwarf.tablero.TableroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadisticaService {

    private EstadisticaRepository estatsrepo;
    private TableroRepository tableroRepository;

    @Autowired
    public EstadisticaService(EstadisticaRepository estatsrepo, TableroRepository tableroRepository) {
        this.estatsrepo = estatsrepo;
        this.tableroRepository = tableroRepository;
    }

    @Transactional
    public void saveEstadistica(Estadistica estadistica) throws DataAccessException {
        estatsrepo.save(estadistica);
    }

    // Partida ya finalizada
    @Transactional
    public void actualizarEstadistica(Tablero tablero) {
        for (Jugador jugador : tablero.getJugadores()) {
            User user = jugador.getUser();
            Estadistica estadistica = user.getEstadistica();

            estadistica.setAcero(estadistica.getAcero() + jugador.getAcero());
            estadistica.setHierro(estadistica.getHierro() + jugador.getHierro());
            estadistica.setMedallas(estadistica.getMedallas() + jugador.getMedalla());
            estadistica.setOro(estadistica.getOro() + jugador.getOro());
            estadistica.setObjetos(estadistica.getObjetos() + jugador.getObjeto());
            estadistica.setPuntos(
                    estadistica.getPuntos() + (tablero.getJugadores().size() + 1 - jugador.getPosicionFinal()));
            if (jugador.getPosicionFinal() == 1)
                estadistica.setPartidasGanadas(estadistica.getPartidasGanadas() + 1);
            else
                estadistica.setPartidasPerdidas(estadistica.getPartidasPerdidas() + 1);
        }
    }

}
