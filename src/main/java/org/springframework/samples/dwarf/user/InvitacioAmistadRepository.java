package org.springframework.samples.dwarf.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitacioAmistadRepository extends CrudRepository<InvitacionAmistad, Integer> {
    InvitacionAmistad save(InvitacionAmistad invitacion);
}
