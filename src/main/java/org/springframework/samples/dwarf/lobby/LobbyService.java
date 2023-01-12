package org.springframework.samples.dwarf.lobby;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class LobbyService {
    LobbyRepository lobbyRepo;

    InvitacioJuegoRepository invitacioJuegoRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepo, InvitacioJuegoRepository invitacioJuegoRepository) {
        this.lobbyRepo = lobbyRepo;
        this.invitacioJuegoRepository = invitacioJuegoRepository;
    }

    public Boolean condicionCantidadUsuarios(Lobby lobby) {
        return lobby.getUsuarios().size() > 2;
    }

    public Boolean condicionEstaPresente(Optional<User> user) {
        return !user.isPresent();
    }

    public Boolean condicionAmigoEnLobby(Lobby lobby, User userSearched) {
        return lobby.getUsuarios().stream().anyMatch(usr -> usr.getUsername().equals(userSearched.getUsername()));
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

    @Transactional(readOnly = true)
    public List<Lobby> findByUser(User user) {
        return lobbyRepo.findByUser(user);
    }

    @Transactional
    public void creacionLobby(Lobby lobby, User user) {

        lobby.setUsuarios(new ArrayList<User>());

        lobby.getUsuarios().add(user);

        lobby.setNumUsuarios(1);

        lobby.setAdmin(user.getUsername());

        saveLobby(lobby);
    }

    @Transactional
    public void añadirAmigo(Lobby lobby, User userSearched, User admin) {
        lobby.getUsuarios().add(userSearched);
        InvitacionJuego invitacion = new InvitacionJuego();
        invitacion.setUserrecibe(userSearched);
        invitacion.setUserenvia(admin);
        invitacion.setLobbyId(lobby.getId());
        invitacion.setCreatedAt(new Date());
        lobby.setNumUsuarios(lobby.getNumUsuarios() + 1);
        invitacioJuegoRepository.save(invitacion);

    }

    @Transactional
    public void eliminarAmigo(Lobby lobby, User user, List<InvitacionJuego> invitacionesDeJugadores) {
        lobby.getUsuarios().remove(user);
        for (InvitacionJuego invi : invitacionesDeJugadores) {
            if (lobby.getId() == invi.getLobbyId()) {
                invitacioJuegoRepository.delete(invi);
            }
        }
    }

    @Transactional
    public void eliminarLobby(Lobby lobby) {
        for (InvitacionJuego i : invitacioJuegoRepository.findAll()) {
            if (i.getLobbyId().equals(lobby.getId())) {
                invitacioJuegoRepository.delete(i);
            }
        }
        deleteById(lobby.getId());
    }
}
