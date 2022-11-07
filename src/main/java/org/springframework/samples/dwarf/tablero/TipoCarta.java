package org.springframework.samples.dwarf.tablero;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.dwarf.model.NamedEntity;

@Entity
@Table(name = "tipo_carta")
public class TipoCarta extends NamedEntity {
    // Defensa, Extraccion, Ayuda, Forja;
}
