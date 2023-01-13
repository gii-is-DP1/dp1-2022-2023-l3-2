package org.springframework.samples.dwarf.tablero;

import javax.persistence.Entity;

import org.springframework.samples.dwarf.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatLine extends BaseEntity {
    private String mensaje;
    private String username;
}
