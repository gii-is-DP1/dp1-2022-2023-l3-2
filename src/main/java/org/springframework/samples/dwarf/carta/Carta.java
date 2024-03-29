package org.springframework.samples.dwarf.carta;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.model.BaseEntity;
import org.springframework.samples.dwarf.tablero.Enano;
import org.springframework.samples.dwarf.tablero.Tablero;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Carta extends BaseEntity implements CartaInterfaz, Serializable {

    String imagen;

    Integer posicion;



    @ManyToOne
    @JoinColumn(name = "tipo")
    TipoCarta tipo;

    String entrada;

    Integer cantidadentrada;

    String devuelve;

    Integer cantidaddevuelve;

    @Override
    public void accion1(Tablero tablero) {
        // TODO Auto-generated method stub

    }

    @Override
    public void accion2(Tablero tablero, Jugador j, Enano e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void accion3(Tablero tablero, Jugador j, Enano e) {

    }

    @Override
    public void accion4(Tablero tablero, Jugador j, Enano e) {
        // TODO Auto-generated method stub

    }

    @Override
    public String accion5(Tablero tablero, Jugador j) {
        // TODO Auto-generated method stub
        return null;
    }

}
