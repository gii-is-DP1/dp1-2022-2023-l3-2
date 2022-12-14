/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.dwarf.jugador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.dwarf.model.BaseEntity;
import org.springframework.samples.dwarf.model.Person;
import org.springframework.samples.dwarf.tablero.Enano;
import org.springframework.samples.dwarf.user.User;

import lombok.Getter;
import lombok.Setter;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
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

    //
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    @NotAudited
    private User user;
    //

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
