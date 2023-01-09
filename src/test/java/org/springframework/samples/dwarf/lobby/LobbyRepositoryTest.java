package org.springframework.samples.dwarf.lobby;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LobbyRepositoryTest {

    @Autowired
    LobbyRepository lobbyRepository;

    @Test
    public void findByIdTest() {
        Lobby lobby = lobbyRepository.findById(9999);
        assertThat(lobby.getId()).isEqualTo(9999);
    }

    @Test
    public void findAllTest() {
        Collection<Lobby> lobbys = lobbyRepository.findAll();
        assertNotNull(lobbys);
        assertFalse(lobbys.isEmpty());
        assertEquals(1, lobbys.size());
    }

    @Test
    public void deleteByIdTest() {
        lobbyRepository.deleteById(9999);
        assertEquals(0, lobbyRepository.findAll().size());
    }
    
}
