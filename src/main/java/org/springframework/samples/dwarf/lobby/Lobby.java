package org.springframework.samples.dwarf.lobby;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class Lobby extends NamedEntity {

    @ManyToMany
    @JoinTable(name = "usuarios_lobby", joinColumns = @JoinColumn(name = "user_id"))
    private List<User> usuarios;

    private Integer numUsuarios;

    private String admin;

    private Integer tableroId;
}
