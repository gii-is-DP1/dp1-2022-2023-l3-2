package org.springframework.samples.dwarf.logro;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.dwarf.model.NamedEntity;

@Entity
@Table(name = "tipo_logro")
public class TipoLogro extends NamedEntity{
    // partidasGanadas, recursosConseguidos, puntajeMasAlto;
}
