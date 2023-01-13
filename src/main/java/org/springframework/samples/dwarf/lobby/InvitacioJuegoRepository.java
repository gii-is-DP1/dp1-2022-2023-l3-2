package org.springframework.samples.dwarf.lobby;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dwarf.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitacioJuegoRepository extends CrudRepository<InvitacionJuego, Integer> {
    InvitacionJuego save(InvitacionJuego invitacion);

    List<InvitacionJuego> findAll();

    List<InvitacionJuego> findByUserenvia(User user);

    @Query("SELECT invi FROM InvitacionJuego invi WHERE invi.userenvia =:userenvia AND invi.userrecibe =:userrecibe")
    List<InvitacionJuego> findBoth(User userenvia, User userrecibe);

    @Query("SELECT u FROM InvitacionJuego u WHERE u.userenvia=:user OR u.userrecibe=:user")
    List<InvitacionJuego> findInvitacionesByUser(User user);

}
