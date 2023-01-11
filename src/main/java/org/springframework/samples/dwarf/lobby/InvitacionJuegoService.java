package org.springframework.samples.dwarf.lobby;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvitacionJuegoService {
    private InvitacioJuegoRepository invitacionrepo;

    @Autowired
    public InvitacionJuegoService(InvitacioJuegoRepository invitacionrepo) {
        this.invitacionrepo = invitacionrepo;
    }

    @Transactional
    public List<InvitacionJuego> findAll() {
        return invitacionrepo.findAll();
    }

    @Transactional
    public void saveInvitacionAmistad(InvitacionJuego invitacion) {
        invitacionrepo.save(invitacion);
    }

    @Transactional
    public List<String> findFriendsUser(User user) {
        return invitacionrepo.findByUserenvia(user).stream().map(i -> i.getUserrecibe().getUsername()).toList();
    }

    @Transactional
    public List<InvitacionJuego> findBoth(User userenvia, User userrecibe) {
        return invitacionrepo.findBoth(userenvia, userrecibe);
    }

    @Transactional
    public void delInvi(InvitacionJuego invi) {
        invitacionrepo.delete(invi);
    }

    @Transactional(readOnly = true)
    public List<InvitacionJuego> findInvitacionesByUser(User user) {
        return invitacionrepo.findInvitacionesByUser(user);
    }
}
