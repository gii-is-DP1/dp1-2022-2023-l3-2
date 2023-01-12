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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/")
    public String processTablero(@Valid Lobby lobby, BindingResult result) {
        if (result.hasErrors()) {
            return lobbyForm;
        }
        // Añadimos el usuario autenticado a la lobby
        lobbyService.creacionLobby(lobby, userService.findAuthenticatedUser());
        return "redirect:/lobby/" + lobby.getId();
    }

    @GetMapping("/{lobbyId}")
    public String showLobby(@PathVariable("lobbyId") Integer id, Model model, HttpServletResponse response) {

        Lobby lobby = lobbyService.findById(id);
        User currentUser = userService.findAuthenticatedUser();
        model.addAttribute("lobbyName", lobby.getName());
        model.addAttribute("lobbyId", lobby.getId());
        model.addAttribute("usuarios", lobby.getUsuarios());
        model.addAttribute("user", new User());
        model.addAttribute("isAdmin", currentUser.getUsername().equals(lobby.getAdmin()));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("lobbyAdmin", lobby.getAdmin());
        model.addAttribute("usernames", lobby.getUsuarios().stream().map(user -> user.getUsername()).toList());
        model.addAttribute("tablero", new Tablero());

        return showLobby;
    }



    @GetMapping("/{lobbyId}/add-user")
    public String addUserAlternative(@PathVariable("lobbyId") Integer id, @RequestParam String exactUsername) {
        Lobby lobby = lobbyService.findById(id);

        // Lobby no puede tener mas de 3 users
        if (lobbyService.condicionCantidadUsuarios(lobby)) {
            return "redirect:/lobby/" + lobby.getId();
        }

        Optional<User> user = userService.findUser(exactUsername);

        if (lobbyService.condicionEstaPresente(user)) {
            return "redirect:/lobby/" + lobby.getId();
        }
        User userSearched = user.get();
        // No puedes añadir un usuario que ya está
        if (lobbyService.condicionAmigoEnLobby(lobby, userSearched)) {
            return "redirect:/lobby/" + lobby.getId();
        }
        // No es amigo
        if (invitacionAmistadService.condicionNoAmigo(lobby, userSearched,
                userService.findUser(lobby.getAdmin()).get())) {
            return "redirect:/lobby/" + lobby.getId();
        }

        invitacionJuegoService.saveInvitacionAmistad(
                lobbyService.añadirAmigo(lobby, userSearched, userService.findUser(lobby.getAdmin()).get()));

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

    @GetMapping("/{lobbyId}/delete-user")
    public String deleteUser(@PathVariable("lobbyId") Integer id, Model model, @RequestParam String username) {

        Lobby lobby = lobbyService.findById(id);

        Optional<User> user1 = userService.findUser(username);

        if (lobbyService.condicionEstaPresente(user1)) {
            return "redirect:/lobby/" + lobby.getId();
        }
        User user = user1.get();
        lobbyService.eliminarAmigo(lobby, user, invitacionJuegoService
                .findBoth(userService.findUser(lobby.getAdmin()).get(), user));

        return "redirect:/lobby/" + lobby.getId();
    }

    @GetMapping("/{lobbyId}/delete")
    public String deleteLobby(@PathVariable("lobbyId") Integer id) {

        lobbyService.eliminarLobby(lobbyService.findById(id));

        return "redirect:/";
    }
}
