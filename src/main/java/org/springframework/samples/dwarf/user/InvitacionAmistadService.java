package org.springframework.samples.dwarf.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.dwarf.lobby.Lobby;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvitacionAmistadService {
    private InvitacioAmistadRepository invitacionrepo;
    private UserService userService;

    public Boolean condicionNoAmigo(Lobby lobby, User userSearched, User admin) {
        return !findFriendsUser(admin)
                .contains(userSearched.getUsername());
    }

    @Autowired
    public InvitacionAmistadService(InvitacioAmistadRepository invitacionrepo, @Lazy UserService userService) {
        this.invitacionrepo = invitacionrepo;
        this.userService = userService;
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
    public List<InvitacionAmistad> findFriends(User user) {
        return invitacionrepo.findByUserenvia(user);
    }

    @Transactional(readOnly = true)
    public List<InvitacionAmistad> findInvitacionesByUser(User user) {
        return invitacionrepo.findInvitacionesByUser(user);
    }

    @Transactional
    public void deleteInvitacionAmistad(InvitacionAmistad invitacion) {
        invitacionrepo.delete(invitacion);
    }

    @Transactional
    public void saveInvitacionAmistadFromForm(String enviaUsername, User recibe) {
        InvitacionAmistad invitacionAmistad = new InvitacionAmistad();
        invitacionAmistad.setUserenvia(userService.findUser(enviaUsername).get());
        invitacionAmistad.setUserrecibe(recibe);
        invitacionAmistad.setCreatedAt(new Date());
        saveInvitacionAmistad(invitacionAmistad);
    }
}
