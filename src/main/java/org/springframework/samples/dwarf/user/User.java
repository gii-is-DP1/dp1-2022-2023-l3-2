package org.springframework.samples.dwarf.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    String username;

    String password;

    boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Authorities> authorities;

    @OneToOne(mappedBy = "usuario")
    private Estadistica estadistica;
}
