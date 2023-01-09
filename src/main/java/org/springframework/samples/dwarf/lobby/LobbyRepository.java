package org.springframework.samples.dwarf.lobby;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.dwarf.user.User;

@org.springframework.stereotype.Repository
public interface LobbyRepository extends Repository<Lobby, String> {
    void save(Lobby lobby) throws DataAccessException;

    Lobby findById(Integer id);

    public Collection<Lobby> findAll();

    void deleteById(Integer id);

    @Query("SELECT lobbies FROM Lobby lobbies WHERE :user MEMBER OF lobbies.usuarios")
    public List<Lobby> findByUser(User user);
}
