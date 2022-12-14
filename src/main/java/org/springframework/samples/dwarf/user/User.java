package org.springframework.samples.dwarf.user;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.springframework.samples.dwarf.jugador.Jugador;

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

    String imgperfil;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Authorities> authorities;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Estadistica estadistica;

    public boolean hasRole(String role) {
        return this.authorities.stream().anyMatch(auth -> auth.getAuthority().equals(role));
    }
}
