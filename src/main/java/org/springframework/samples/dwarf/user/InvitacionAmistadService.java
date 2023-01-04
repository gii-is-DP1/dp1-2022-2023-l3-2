package org.springframework.samples.dwarf.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvitacionAmistadService {
    private InvitacioAmistadRepository invitacionrepo;

    @Autowired
    public InvitacionAmistadService(InvitacioAmistadRepository invitacionrepo) {
        this.invitacionrepo = invitacionrepo;
    }

    @Transactional
    public void saveInvitacionAmistad(InvitacionAmistad invitacion) {
        invitacionrepo.save(invitacion);
    }

    @Transactional
    public List<String> findFriendsUser(User user) {
        return invitacionrepo.findByUserenvia(user).stream().map(i -> i.getUserrecibe().username).toList();
    }

    @Transactional
    public List<InvitacionAmistad> findFriends(User user){
        return invitacionrepo.findByUserenvia(user);
    }
}
