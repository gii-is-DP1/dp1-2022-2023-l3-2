package org.springframework.samples.dwarf.user;

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
}
