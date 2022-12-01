package org.springframework.samples.dwarf.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitacioAmistadRepository extends CrudRepository<InvitacionAmistad, Integer> {
    InvitacionAmistad save(InvitacionAmistad invitacion);

   
    List<InvitacionAmistad> findByUserenvia(User user);

}
