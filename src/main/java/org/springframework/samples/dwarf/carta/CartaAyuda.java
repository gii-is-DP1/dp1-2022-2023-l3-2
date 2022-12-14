package org.springframework.samples.dwarf.carta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "ayuda")
public class CartaAyuda extends Carta {

}