package org.springframework.samples.dwarf.lobby;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.dwarf.model.BaseEntity;
import org.springframework.samples.dwarf.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class InvitacionJuego extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id_envia")
    private User userenvia;

    @OneToOne
    @JoinColumn(name = "user_id_recibe")
    private User userrecibe;

    Integer lobbyId;
}
