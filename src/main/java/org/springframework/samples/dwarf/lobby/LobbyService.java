package org.springframework.samples.dwarf.lobby;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LobbyService {
    LobbyRepository lobbyRepo;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepo) {
        this.lobbyRepo = lobbyRepo;
    }

    @Transactional(readOnly = true)
    public Lobby findById(Integer id) {
        return lobbyRepo.findById(id);
    }

    @Transactional
    public void deleteById(Integer id) {
        lobbyRepo.deleteById(id);
    }

    @Transactional
    public void saveLobby(Lobby lobby) {
        lobbyRepo.save(lobby);
    }

    @Transactional(readOnly = true)
    public List<Lobby> findAll() {
        return (List<Lobby>) lobbyRepo.findAll();
    }

}
