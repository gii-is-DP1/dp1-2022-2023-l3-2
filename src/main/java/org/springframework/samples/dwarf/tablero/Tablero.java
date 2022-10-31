package org.springframework.samples.dwarf.tablero;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.dwarf.model.NamedEntity;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Entity
@Getter
@Setter
public class Tablero extends NamedEntity {

    @OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "mazo_nuevo", joinColumns = @JoinColumn(name="mazo_id"))
	private List<Mazo> mazos;


}
