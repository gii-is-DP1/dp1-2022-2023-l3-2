package org.springframework.samples.dwarf.user;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.dwarf.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "estadistica")
public class Estadistica extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User usuario;

    Integer partidasGanadas;
    Integer partidasPerdidas;
    Integer puntos;
    Integer hierro;
    Integer acero;
    Integer oro;
    Integer objetos;
    Integer medallas;

    public Map<String, Double> getPromedios() {
        Map<String, Double> promedios = new HashMap<>();

        Integer partidasJugadas = ((this.partidasGanadas + this.partidasPerdidas) > 0)
                ? this.partidasGanadas + this.partidasPerdidas
                : 1;

        promedios.put("oro", (this.oro + .0) / partidasJugadas);
        promedios.put("hierro", (this.hierro + .0) / partidasJugadas);
        promedios.put("acero", (this.acero + .0) / partidasJugadas);
        promedios.put("objetos", (this.objetos + .0) / partidasJugadas);
        promedios.put("medallas", (this.medallas + .0) / partidasJugadas);

        return promedios;
    }
}
