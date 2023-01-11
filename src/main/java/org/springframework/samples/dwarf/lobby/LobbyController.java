package org.springframework.samples.dwarf.lobby;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.samples.dwarf.tablero.Tablero;
import org.springframework.samples.dwarf.user.InvitacionAmistadService;
import org.springframework.samples.dwarf.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/lobby")
public class LobbyController {

    private String lobbyForm = "lobby/lobbyForm";
    private String showLobby = "lobby/showLobby";

    private LobbyService lobbyService;
    private UserService userService;
    private InvitacionAmistadService invitacionAmistadService;
    private InvitacionJuegoService invitacionJuegoService;

    @Autowired
    public LobbyController(LobbyService lobbyService, UserService userService,
            InvitacionAmistadService invitacionAmistadService, InvitacionJuegoService invitacionJuegoService) {
        this.lobbyService = lobbyService;
        this.userService = userService;
        this.invitacionAmistadService = invitacionAmistadService;
        this.invitacionJuegoService = invitacionJuegoService;
    }

    @GetMapping("/")
    public String showLobbyForm(Map<String, Object> model) {
        Lobby lobby = new Lobby();
        model.put("lobby", lobby);
        return lobbyForm;
    }

    @Transactional
    @PostMapping("/")
    public String processTablero(@Valid Lobby lobby, BindingResult result) {
        if (result.hasErrors()) {
            return lobbyForm;
        }

        // Añadimos el usuario autenticado a la lobby

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) authentication
                .getPrincipal();

        lobby.setUsuarios(new ArrayList<User>());

        lobby.getUsuarios().add(userService.findUserByString(currentUser.getUsername()).get(0));

        lobby.setNumUsuarios(1);

        lobby.setAdmin(currentUser.getUsername());

        lobbyService.saveLobby(lobby);

        return "redirect:/lobby/" + lobby.getId();
    }

    @GetMapping("/{lobbyId}")
    public String showLobby(@PathVariable("lobbyId") Integer id, Model model, HttpServletResponse response) {

        Lobby lobby = lobbyService.findById(id);

        model.addAttribute("lobbyName", lobby.getName());
        model.addAttribute("lobbyId", lobby.getId());
        model.addAttribute("usuarios", lobby.getUsuarios());
        model.addAttribute("user", new User());

        User currentUser = userService.findAuthenticatedUser();

        model.addAttribute("isAdmin", currentUser.getUsername().equals(lobby.getAdmin()));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("lobbyAdmin", lobby.getAdmin());
        model.addAttribute("usernames", lobby.getUsuarios().stream().map(user -> user.getUsername()).toList());
        model.addAttribute("tablero", new Tablero());

        return showLobby;
    }

    @Transactional
    @PostMapping("/{lobbyId}/add-user")
    public String addUser(@PathVariable("lobbyId") Integer id, @Valid User user, BindingResult result) {

        Lobby lobby = lobbyService.findById(id);

        if (result.hasErrors()) {
            return "redirect:/lobby" + lobby.getId();
        }

        // Lobby no puede tener mas de 3 users
        if (lobby.getUsuarios().size() > 2) {
            return "redirect:/lobby/" + lobby.getId();
        }

        Optional<User> user1 = userService.findUser(user.getUsername());

        if (!user1.isPresent()) {
            return "redirect:/lobby/" + lobby.getId();
        }
        User userSearched = user1.get();

        // No puedes añadir un usuario que ya está
        if (lobby.getUsuarios().stream().anyMatch(usr -> usr.getUsername().equals(userSearched.getUsername()))) {
            return "redirect:/lobby/" + lobby.getId();
        }
        // No es su amigo
        if (!invitacionAmistadService.findFriendsUser(userService.findUser(lobby.getAdmin()).get())
                .contains(userSearched.getUsername())) {
            return "redirect:/lobby/" + lobby.getId();
        }

        lobby.getUsuarios().add(userSearched);
        InvitacionJuego invitacion = new InvitacionJuego();
        invitacion.setUserrecibe(userSearched);
        invitacion.setUserenvia(userService.findUser(lobby.getAdmin()).get());
        invitacion.setLobbyId(lobby.getId());
        invitacion.setCreatedAt(new Date());
        invitacionJuegoService.saveInvitacionAmistad(invitacion);
        lobby.setNumUsuarios(lobby.getNumUsuarios() + 1);

        return "redirect:/lobby/" + lobby.getId();
    }

    @Transactional
    @GetMapping("/{lobbyId}/add-user")
    public String addUserAlternative(@PathVariable("lobbyId") Integer id, @RequestParam String exactUsername) {
        Lobby lobby = lobbyService.findById(id);

        // Lobby no puede tener mas de 3 users
        if (lobby.getUsuarios().size() > 2) {
            return "redirect:/lobby/" + lobby.getId();
        }

        Optional<User> user = userService.findUser(exactUsername);

        if (!user.isPresent()) {
            return "redirect:/lobby/" + lobby.getId();
        }
        User userSearched = user.get();
        // No puedes añadir un usuario que ya está
        if (lobby.getUsuarios().stream().anyMatch(usr -> usr.getUsername().equals(userSearched.getUsername()))) {
            return "redirect:/lobby/" + lobby.getId();
        }
        // No es amigo
        if (!invitacionAmistadService.findFriendsUser(userService.findUser(lobby.getAdmin()).get())
                .contains(userSearched.getUsername())) {
            return "redirect:/lobby/" + lobby.getId();
        }

        lobby.getUsuarios().add(userSearched);
        InvitacionJuego invitacion = new InvitacionJuego();
        invitacion.setUserrecibe(userSearched);
        invitacion.setUserenvia(userService.findUser(lobby.getAdmin()).get());
        invitacion.setLobbyId(lobby.getId());
        invitacion.setCreatedAt(new Date());
        invitacionJuegoService.saveInvitacionAmistad(invitacion);
        lobby.setNumUsuarios(lobby.getNumUsuarios() + 1);

        return "redirect:/lobby/" + lobby.getId();
    }

    @GetMapping("/users")
    @ResponseBody
    public String getUsersSearched(@RequestParam("id") Integer lobbyId, @RequestParam String q) {

        if (q.equals(""))
            return "{\"data\":[]}";

        Lobby lobby = lobbyService.findById(lobbyId);

        List<User> usersSearched1 = userService.findUserByString(q);

        // Solo amigos
        usersSearched1 = usersSearched1.stream()
                .filter(usr -> invitacionAmistadService.findFriendsUser(userService.findUser(lobby.getAdmin()).get())
                        .contains(usr.getUsername()))
                .toList();

        List<String> usersSearched = usersSearched1.stream()
                .map(usr -> "\"" + usr.getUsername() + "\"").toList();

        String JSON = "{\"data\": [";

        JSON += String.join(",", usersSearched);

        JSON += "]}";

        return JSON;
    }

    @Transactional
    @GetMapping("/{lobbyId}/delete-user")
    public String deleteUser(@PathVariable("lobbyId") Integer id, Model model, @RequestParam String username) {

        Lobby lobby = lobbyService.findById(id);

        Optional<User> user1 = userService.findUser(username);

        if (!user1.isPresent()) {
            return "redirect:/lobby/" + lobby.getId();
        }
        User user = user1.get();

        lobby.getUsuarios().remove(user);
        List<InvitacionJuego> invitacionesDeJugadores = invitacionJuegoService
                .findBoth(userService.findUser(lobby.getAdmin()).get(), user);
        for (InvitacionJuego invi : invitacionesDeJugadores) {
            if (lobby.getId() == invi.getLobbyId()) {
                invitacionJuegoService.delInvi(invi);
            }
        }
        return "redirect:/lobby/" + lobby.getId();
    }

    @Transactional
    @GetMapping("/{lobbyId}/delete")
    public String deleteLobby(@PathVariable("lobbyId") Integer id) {

        for (InvitacionJuego i : invitacionJuegoService.findAll()) {
            if (i.getLobbyId().equals(id)) {
                invitacionJuegoService.delInvi(i);
            }
        }

        lobbyService.deleteById(id);
        return "redirect:/";
    }
}
