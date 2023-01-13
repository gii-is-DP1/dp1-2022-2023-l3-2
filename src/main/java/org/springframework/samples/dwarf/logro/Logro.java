package org.springframework.samples.dwarf.logro;

import javax.persistence.Entity;
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
    @Min(value=1)
    private Integer dificultad;

    @NotNull
    private Integer requisito;

    @ManyToOne
    @JoinColumn(name = "tipo")
    private TipoLogro tipo;

}
