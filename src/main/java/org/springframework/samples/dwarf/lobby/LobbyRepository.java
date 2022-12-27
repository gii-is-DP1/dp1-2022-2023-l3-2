package org.springframework.samples.dwarf.lobby;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface LobbyRepository extends Repository<Lobby, String> {
    void save(Lobby lobby) throws DataAccessException;

    Lobby findById(Integer id);

    public Collection<Lobby> findAll();

    void deleteById(Integer id);
}
