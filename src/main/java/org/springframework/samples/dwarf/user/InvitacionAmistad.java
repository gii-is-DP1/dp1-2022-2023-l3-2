package org.springframework.samples.dwarf.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.samples.dwarf.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class InvitacionAmistad extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id_envia")
    private User userenvia;

    @OneToOne
    @JoinColumn(name = "user_id_recibe")
    private User userrecibe;

    private Date createdAt;
}
