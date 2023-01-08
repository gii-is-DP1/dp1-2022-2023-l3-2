package org.springframework.samples.dwarf.lobby;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dwarf.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitacioJuegoRepository extends CrudRepository<InvitacionJuego, Integer> {
    InvitacionJuego save(InvitacionJuego invitacion);

    List<InvitacionJuego> findByUserenvia(User user);

    @Query("SELECT invi FROM InvitacionJuego invi WHERE invi.userenvia =:userenvia AND invi.userrecibe =:userrecibe")
    List<InvitacionJuego> findBoth(User userenvia, User userrecibe);

}
