package org.springframework.samples.dwarf.carta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "base")
public class CartaBase extends Carta {

}
