package org.springframework.samples.dwarf.tablero;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.samples.dwarf.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Entity
@Getter
@Setter

public class Mazo extends NamedEntity{

    private String posicion;
    
}
