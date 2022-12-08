package org.springframework.samples.dwarf.tablero;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.model.NamedEntity;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Entity
@Getter
@Setter
public class Tablero extends NamedEntity {

    Integer ronda;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mazo_tablero", joinColumns = @JoinColumn(name = "tablero"))
    private List<Mazo> mazos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "chat_tablero", joinColumns = @JoinColumn(name = "tablero"))
    private List<ChatLine> chat;

    @ManyToMany
    @JoinTable(name = "jugadores_lobby", joinColumns = @JoinColumn(name = "jugador_id"))
    private List<Jugador> jugadores;

    public Integer getNumJugadores() {
        return jugadores.size();
    }

    public boolean estaEnTablero(int cartaId) {
        int res = mazos.stream().filter(mazo -> mazo.getFirstCarta().getId() == cartaId).toList().size();
        return res >= 1 ? true : false;
    }

    public boolean tieneEnanoEncima(int cartaId) {
        List<Enano> todosLosEnanos = new ArrayList<>();
        for (Jugador j : this.jugadores) {
            for (Enano e : j.getEnano()) {
                todosLosEnanos.add(e);
            }
        }
        List<Mazo> mazosConEnanoEncima = todosLosEnanos.stream().filter(e -> e.getMazo() != null)
                .map(e -> e.getMazo()).toList();

        int res = mazos.stream().filter(mazo -> mazo.getFirstCarta().getId() == cartaId).toList().size();

        return ((res >= 1)
                && mazosConEnanoEncima.stream().map(mazo -> mazo.getFirstCarta().getId()).toList().contains(cartaId))
                        ? true
                        : false;

    }

    public Jugador getJugadorByUsername(String username) {
        return jugadores.stream().filter(jugador -> jugador.getUser().getUsername().equals(username)).toList().get(0);
    }

    public void setTurnoByUsername(String username) {
        jugadores.stream().forEach(jugador -> jugador.setTurno(false));
        getJugadorByUsername(username).setTurno(true);
    }

    public boolean analizarDefensas() {
        final List<Integer> ORCOS = Arrays.asList(11, 20, 30, 49);
        boolean farmeo = !ORCOS.stream()
                .anyMatch(cartaId -> this.estaEnTablero(cartaId) && !this.tieneEnanoEncima(cartaId));

        final List<Integer> GRAN_DRAGONES = Arrays.asList(13, 48);
        if (GRAN_DRAGONES.stream()
                .anyMatch(cartaId -> this.estaEnTablero(cartaId) && !this.tieneEnanoEncima(cartaId))) {
            // Si no se defiende le quita 1 de oro a cada jugador
            this.getJugadores().forEach(jugador -> {
                if (jugador.getOro() > 0)
                    jugador.setOro(jugador.getOro() - 1);
            });
        }

        final List<Integer> DRAGONES = Arrays.asList(22, 27, 35);
        if (DRAGONES.stream()
                .anyMatch(cartaId -> this.estaEnTablero(cartaId) && !this.tieneEnanoEncima(cartaId))) {
            // Si no se defiende le quita 1 de oro a cada jugador
            this.getJugadores().forEach(jugador -> {
                if (jugador.getOro() > 0)
                    jugador.setOro(jugador.getOro() - 1);
            });
        }
        final List<Integer> KNOCKERS = Arrays.asList(14, 42, 43, 53);
        if (KNOCKERS.stream()
                .anyMatch(cartaId -> this.estaEnTablero(cartaId) && !this.tieneEnanoEncima(cartaId))) {
            // Si no se defiende le quita 1 de hierro a cada jugador
            this.getJugadores().forEach(jugador -> {
                if (jugador.getHierro() > 0)
                    jugador.setHierro(jugador.getHierro() - 1);
            });
        }

        final List<Integer> SIDHES = Arrays.asList(37, 38, 47);
        if (SIDHES.stream().anyMatch(cartaId -> this.estaEnTablero(cartaId) && !this.tieneEnanoEncima(cartaId))) {
            // Si no se defiende cambia 2 oro por 2 hierro
            this.getJugadores().forEach(jugador -> {
                if (jugador.getOro() >= 2) {
                    jugador.setOro(jugador.getOro() - 2);
                    jugador.setHierro(jugador.getHierro() + 2);
                }
            });
        }

        return farmeo;
    }
}
