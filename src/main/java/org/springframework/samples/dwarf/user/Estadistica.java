package org.springframework.samples.dwarf.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
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
}
