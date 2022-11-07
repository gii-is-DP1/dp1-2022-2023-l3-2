package org.springframework.samples.dwarf.logro;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.samples.dwarf.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Logro extends NamedEntity {

    private String descripcion;

    @NotNull
    @Max(value=3)
    @Min(value=0)
    private Integer dificultad;

    private String requisito;

    @ManyToOne
    @JoinColumn(name = "tipo", referencedColumnName = "name")
    private TipoLogro tipo;
}
