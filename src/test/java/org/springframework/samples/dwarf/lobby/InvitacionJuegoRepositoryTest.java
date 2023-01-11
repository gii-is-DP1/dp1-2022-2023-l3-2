package org.springframework.samples.dwarf.lobby;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserRepository;

@DataJpaTest
public class InvitacionJuegoRepositoryTest {
    
    @Autowired
    InvitacioJuegoRepository invitacionJuegoRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUserenviaTest() {
        Optional<User> jualeomad = userRepository.findById("jualeomad");
        List<InvitacionJuego> invs = invitacionJuegoRepository.findByUserenvia(jualeomad.get());
        assertEquals(0, invs.size());
    }

    @Test
    public void findBothTest() {
        Optional<User> jualeomad = userRepository.findById("jualeomad");
        Optional<User> rafgargal = userRepository.findById("rafgargal");
        List<InvitacionJuego> invs = invitacionJuegoRepository.findBoth(jualeomad.get(), rafgargal.get());
        assertEquals(0, invs.size());
    }
}
