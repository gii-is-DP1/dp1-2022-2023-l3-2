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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.model.NamedEntity;
import org.springframework.samples.dwarf.user.User;

import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
public class Tablero extends NamedEntity {

    Integer ronda;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mazo_tablero", joinColumns = @JoinColumn(name = "tablero"))
    private List<Mazo> mazos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "chat_tablero", joinColumns = @JoinColumn(name = "tablero"))
    private List<ChatLine> chat;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "jugadores_lobby", joinColumns = @JoinColumn(name = "jugador_id"))
    private List<Jugador> jugadores;

    private boolean defensaTotal;

    private boolean terminada;

    @DateTimeFormat(pattern = "dd-MM-yyyy/HH:mm:ss")
    private Date createdAt;

    @DateTimeFormat(pattern = "dd-MM-yyyy/HH:mm:ss")
    private Date finishedAt;

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

        if (this.defensaTotal) {
            this.setDefensaTotal(false);
            return true;
        }

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

    public String getChatLinesInJSON() {
        String JSON = "{\"messages\": [";

        List<String> rawObjects = new ArrayList<>();
        // for (ChatLine chatLine : this.getChat()) {

        // rawMessages.add(String.format("\"(%s): %s\"", chatLine.getUsername(),
        // chatLine.getMensaje()));
        // }

        for (ChatLine chatLine : this.getChat()) {

            rawObjects.add(String.format("{\"username\": \"%s\", \"message\": \"%s\"}", chatLine.getUsername(),
                    chatLine.getMensaje()));
        }

        JSON += String.join(",", rawObjects);

        JSON += "]}";

        return JSON;
    }

    public Long secondsDiffBetweenTwoDates(Date startDate, Date endDate) {
        Date startDateObj = startDate;
        Date endDateObj = endDate;
        // startDateObj.getTime() method gives date in milli seconds format
        System.out.println("Time in milli seconds: " + startDateObj.getTime());

        // find time difference in milli seconds
        long timeDiff = endDateObj.getTime() - startDateObj.getTime();
        System.out.println("Time difference in Milli seconds: " + timeDiff);

        // time difference in seconds
        Long secondsDiff = (timeDiff / 1000);
        System.out.println("Time difference in seconds: " + secondsDiff);

        return secondsDiff;

        // // time difference in minutes
        // long minDiff = timeDiff / (1000 * 60);
        // System.out.println("Time difference in minutes: " + minDiff);

        // // time difference in minutes
        // long hoursDiff = timeDiff / (1000 * 60 * 60);
        // System.out.println("Time difference in hours: " + hoursDiff);

        // // time difference in minutes
        // long daysDiff = timeDiff / (1000 * 60 * 60 * 24);
        // System.out.println("Time difference in days: " + daysDiff);
    }

    public String secondsToHoursMinutesSeconds(Long totalSecs) {
        Double hours = totalSecs.doubleValue() / 3600;
        Double minutes = (totalSecs.doubleValue() % 3600) / 60;
        Double seconds = totalSecs.doubleValue() % 60;

        return String.format("%02d:%02d:%02d", (int) Math.floor(hours), (int) Math.floor(minutes),
                (int) Math.floor(seconds));
    }

    // Dos posibilidades:
    // 1- Si la partida esta terminada devuelve duracion ed la partida
    // 2- Si no esta terminada devuelve tiempo real
    public String getFormattedDuration() {
        if (this.terminada)
            return secondsToHoursMinutesSeconds(secondsDiffBetweenTwoDates(this.createdAt, this.finishedAt));
        return secondsToHoursMinutesSeconds(secondsDiffBetweenTwoDates(this.createdAt, new Date()));
    }
}
