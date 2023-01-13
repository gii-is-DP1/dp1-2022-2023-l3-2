package org.springframework.samples.dwarf.jugador;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.samples.dwarf.model.BaseEntity;
import org.springframework.samples.dwarf.tablero.Enano;
import org.springframework.samples.dwarf.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "jugadores")
@Audited
public class Jugador extends BaseEntity {

    private boolean turno;

    private boolean primerjugador;

    private boolean esespectador;

    private Integer acero;
    private Integer medalla;
    private Integer oro;
    private Integer hierro;
    private Integer objeto;

    private Integer enanosDisponibles;

    @Column(name = "posicion_final")
    private Integer posicionFinal;

    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    @NotAudited
    private User user;
    

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "enano_jugador", joinColumns = @JoinColumn(name = "enano"))
    @NotAudited
    private List<Enano> enano;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Jugador crearJugadorInicial(Boolean primerJugador) {

        Jugador jugador = new Jugador();

        jugador.setAcero(0);
        jugador.setHierro(0);
        jugador.setMedalla(0);
        jugador.setObjeto(0);
        jugador.setOro(0);
        jugador.setPrimerjugador(primerJugador);
        jugador.setTurno(false); // Se setea despues automaticamente
        jugador.setEsespectador(false);

        return jugador;
    }

}
