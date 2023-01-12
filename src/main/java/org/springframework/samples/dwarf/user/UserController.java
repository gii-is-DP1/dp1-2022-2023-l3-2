/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.dwarf.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.h2.expression.analysis.PartitionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.lobby.InvitacionJuego;
import org.springframework.samples.dwarf.lobby.InvitacionJuegoService;
import org.springframework.samples.dwarf.lobby.Lobby;
import org.springframework.samples.dwarf.lobby.LobbyService;
import org.springframework.samples.dwarf.logro.Logro;
import org.springframework.samples.dwarf.logro.LogroService;
import org.springframework.samples.dwarf.tablero.Tablero;
import org.springframework.samples.dwarf.tablero.TableroService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {


    private static final String view_user = "users/showUser";
    private static final String VIEW_USERS_LIST = "users/usersList";

    private final JugadorService jugadorService;
    private final UserService userService;
    private final LogroService logroService;
    private final InvitacionJuegoService invitacionJuegoService;
    private final InvitacionAmistadService invitacionAmistadService;
    private final AuthoritiesService authoritiesService;
    private final EstadisticaService estadisticaService;
    private final TableroService taservice;
    private final LobbyService lobbyService;

    @Autowired
    public UserController(JugadorService jugadorService, UserService userService, LogroService logroService,
            InvitacionAmistadService invitacionAmistadService, AuthoritiesService authoritiesService,
            EstadisticaService estadisticaService, TableroService taservice,
            InvitacionJuegoService invitacionJuegoService, LobbyService lobbyService) {
        this.jugadorService = jugadorService;
        this.userService = userService;
        this.invitacionJuegoService = invitacionJuegoService;
        this.logroService = logroService;
        this.invitacionAmistadService = invitacionAmistadService;
        this.authoritiesService = authoritiesService;
        this.estadisticaService = estadisticaService;
        this.taservice = taservice;
        this.lobbyService = lobbyService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/users")
    public String parteDeUsuarios(Map<String, Object> model, @RequestParam Integer page) {
        User currentUser = userService.findAuthenticatedUser();
        List<InvitacionJuego> juegos = invitacionJuegoService.findInvitacionesByUser(currentUser);
        List<InvitacionAmistad> amigos = invitacionAmistadService.findInvitacionesByUser(currentUser);
        List<InvitacionJuego> diferencia = new ArrayList<>();
        for (InvitacionJuego invi : juegos) {
            Long time = 0L;
            Date tiempo = new Date(System.currentTimeMillis());
            time = tiempo.getTime() - invi.getCreatedAt().getTime();

            if (!invi.getUserenvia().equals(currentUser)) {
                diferencia.add(invi);
            }
            if (time >= 3600000L) {
                diferencia.remove(invi);
            }
        }
        List<InvitacionAmistad> notificaciones = new ArrayList<>();
        for (InvitacionAmistad invi : amigos) {
            Long time = 0L;
            Date tiempo = new Date(System.currentTimeMillis());
            time = tiempo.getTime() - invi.getCreatedAt().getTime();
            if (!invi.getUserenvia().equals(currentUser)) {
                notificaciones.add(invi);
            }
            if (time >= 3600000L) {
                notificaciones.remove(invi);
            }
        }

        List<List<User>> pages = userService.getPages(userService.findJugadoresSortedByPuntuacion());
        List<User> pagina = pages.get(page);
        if (!diferencia.isEmpty()) {
            model.put("juegos", diferencia);

        }
        if (!notificaciones.isEmpty()) {
            model.put("amigos", notificaciones);

        }
        model.put("perfil", currentUser.getUsername());
        model.put("usuarios", pagina);
        model.put("paginaActual", page);
        model.put("paginas", IntStream.rangeClosed(0, pages.size() - 1)
                .boxed().toList());

        return "users/welcomecopy";
    }

    @GetMapping(value = "/user")
    public String usersList(Map<String, Object> model, @RequestParam Integer page,
            @RequestParam(required = false) String search) {

        List<User> usuarios;

        if (search == null) {
            usuarios = userService.findByRol("jugador");
        } else {
            usuarios = userService.findUserByString(search);
        }

        model.put("puntuacion", userService.getPuntuaciones());
        model.put("usuarios", userService.getPages(usuarios).get(page));
        model.put("paginaActual", page);
        model.put("paginas", IntStream.rangeClosed(0, userService.getPages(usuarios).size() - 1)
                .boxed().toList());
        return VIEW_USERS_LIST;
    }

    @GetMapping(value = "/user/find")
    public String initCreationForm(Map<String, Object> model) {
        User user = new User();
        model.put("user", user);
        return "users/findUsers";
    }

    @PostMapping(value = "/users/find")
    public String processCreationForm(Map<String, Object> model, @Valid User user, BindingResult result,
            RedirectAttributes redatt) {
        if (result.hasErrors()) {
            return "redirect:/user";
        } else {
            // creating owner, user, and authority
            List<User> usersSearched = userService.findUserByString(user.username);
            if (usersSearched.size() == 0) {
                redatt.addFlashAttribute("mensaje", "No hay resultados para la busqueda");
                return "redirect:/user/find";
            }

            if (usersSearched.size() == 1) {
                return "redirect:/users/" + usersSearched.get(0).getUsername();
            }
            // model.put("usuarios", userService.findUserByString(user.username));
            return "redirect:/user?page=0&search=" + user.username;
        }
    }

    @PostMapping(value = "/users/friend")
    public String processAddFriendForm(Map<String, Object> model, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                    .getContext().getAuthentication()
                    .getPrincipal();
            return "redirect:/users/" + currentUser.getUsername();
        } else {
            // creating owner, user, and authority

            String enviaUsername = "";
            User recibe = userService.findUser(user.username).get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                if (authentication.isAuthenticated()) {
                    org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) authentication
                            .getPrincipal();

                    enviaUsername = currentUser.getUsername();

                }
            }
            // si no encuentra al usuario
            if (!userService.findAll().contains(userService.findUser(user.username).get())) {
                return "redirect:/users/" + enviaUsername;
            }

            // amiga que ya esta
            if (invitacionAmistadService.findFriendsUser(userService.findUser(enviaUsername).get())
                    .contains(user.username)) {

                return "redirect:/users/" + enviaUsername;
            }
            InvitacionAmistad invitacionAmistad = new InvitacionAmistad();
            invitacionAmistad.setUserenvia(userService.findUser(enviaUsername).get());
            invitacionAmistad.setUserrecibe(recibe);
            invitacionAmistad.setCreatedAt(new Date());
            invitacionAmistadService.saveInvitacionAmistad(invitacionAmistad);
            model.put("usuarios", invitacionAmistadService.findFriendsUser(userService.findUser(enviaUsername).get()));
            return "redirect:/users/" + enviaUsername;
        }
    }

    @GetMapping(value = "/users/{userid}")
    public String showUser(@PathVariable("userid") String id, Map<String, Object> model,
            @RequestParam(required = false) String seccion) {
        User usuario = userService.findUser(id).get();
        List<Jugador> jugadores = jugadorService.findJugadorUser(id);

        List<Logro> logrosCumplidos = logroService.findLogrosByUsername(id);

        List<User> amigos = invitacionAmistadService.findFriends(usuario).stream()
                .map(invitacion -> invitacion.getUserrecibe()).toList();

        Boolean condicionMod = (userService.findAuthenticatedUser().equals(userService.findUser(id).get())
                || userService.findAuthenticatedUser().getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("admin")));
        List<Boolean> condicionEnline = invitacionAmistadService.findFriends(usuario).stream()
                .map(u -> taservice.findByUser(u.getUserrecibe()).stream().allMatch(t -> t.isTerminada()))
                .toList();

        Map<String, Boolean> condicionEnlinea = new HashMap<>();

        for (int i = 0; i < amigos.size(); i++) {
            condicionEnlinea.put(amigos.get(i).getUsername(), condicionEnline.get(i));
        }

        List<Tablero> partidasEnCurso = taservice.findEnCursoByUser(usuario);
        List<Lobby> lobbies = lobbyService.findByUser(usuario);

        model.put("seccion", seccion);
        model.put("amigos", amigos);
        model.put("imagen", usuario.imgperfil);
        model.put("user", new User());
        model.put("amigosEnLinea", condicionEnlinea);
        model.put("condicion", condicionMod);
        model.put("usuario", usuario);
        model.put("jugadores", jugadores);
        model.put("logros", logrosCumplidos);
        model.put("currentUsername", usuario.username);
        model.put("promedios", usuario.getEstadistica().getPromedios());

        final Integer PARTIDAS_MOSTRADAS = 7;
        model.put("partidas", taservice.findLastNGamesByUser(usuario, PARTIDAS_MOSTRADAS));

        model.put("partidasEnCurso", partidasEnCurso);
        model.put("lobbies", lobbies);

        return view_user;
    }

    @GetMapping(value = "/users/{userid}/delete")
    public String deleteUser(@PathVariable("userid") String id) {
        userService.deleteUserById(id);
        return "redirect:/user/find";
    }

    @GetMapping("usersnew")
    public String createNewUser(Map<String, Object> model) {
        User user = new User();
        model.put("user", user);
        return "users/creatForm";
    }

    @PostMapping("usersnew")
    public String createNewUser(@Valid User user, BindingResult result, RedirectAttributes redatt) {
        if (result.hasErrors()) {
            return "redirect:/usersnew";
        } else {

            if (userService.findUser(user.getUsername()).isPresent()) {
                redatt.addFlashAttribute("mensaje", "Ya existe un usuario con este nombre");
                return "redirect:/usersnew";
            }

            userService.saveUser(user);
            Estadistica estats = new Estadistica();
            estats.setUsuario(user);
            estats.setAcero(0);
            estats.setHierro(0);
            estats.setOro(0);
            estats.setMedallas(0);
            estats.setObjetos(0);
            estats.setPuntos(0);
            estats.setPartidasGanadas(0);
            estats.setPartidasPerdidas(0);
            estadisticaService.saveEstadistica(estats);
            Authorities authority = new Authorities();
            authority.setAuthority("jugador");
            authority.setUser(user);
            authoritiesService.saveAuthorities(authority);
            return "redirect:/";
        }
    }

    @GetMapping("users/mod")
    public String modifyUser(Map<String, Object> model, @RequestParam("user") String id) {
        User actual = userService.findAuthenticatedUser();
        User user = new User();
        model.put("user", user);
        if (!userService.findAuthenticatedUser().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("admin"))) {
            if (!(userService.findUser(id).get().equals(actual))) {
                return view_user;
            }
        }

        return "users/modForm";
    }

    @PostMapping("users/mod")
    public String modifyUser(@Valid User user, BindingResult result, RedirectAttributes redatt,
            @RequestParam("user") String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User currentUser = userService.findAuthenticatedUser();
            if (currentUser.getUsername().equals(id)
                    || userService.findAuthenticatedUser().getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("admin"))) {
                // if (user.getUsername().equals("")) {
                // user.setUsername(userService.findUser(id).get().getUsername());
                // }
                // if (user.getPassword().equals("")) {
                // user.setUsername(userService.findUser(id).get().getPassword());
                // }
                // if (user.getImgperfil().equals("")) {
                // user.setUsername(userService.findUser(id).get().getImgperfil());
                // }
                userService.saveUser(user);
                Authorities authority = new Authorities();
                authority.setAuthority("jugador");
                authority.setUser(user);
                authoritiesService.saveAuthorities(authority);
                return "redirect:/";
            }

            redatt.addFlashAttribute("mensaje", "No eres propietario de este usuario");
            return "redirect:/users/mod";

        }
        return "redirect:/";
    }

    @GetMapping("/users/{userid}/search-friends")
    @ResponseBody
    public String getNotFriends(@PathVariable("userid") String id, @RequestParam String q) {

        User usuario = userService.findUser(id).get();

        if (q.equals(""))
            return "{\"data\":[]}";

        List<User> usersSearched1 = userService.findUserByString(q);

        // No buscar amigos
        usersSearched1 = usersSearched1.stream()
                .filter(usr -> !invitacionAmistadService.findFriendsUser(usuario)
                        .contains(usr.getUsername()) && !usuario.getUsername().equals(usr.getUsername()))
                .toList();

        List<String> usersSearched = usersSearched1.stream()
                .map(usr -> "\"" + usr.getUsername() + "\"").toList();

        String JSON = "{\"data\": [";

        JSON += String.join(",", usersSearched);

        JSON += "]}";

        return JSON;
    }

    // Con sugerencias javascript
    @GetMapping(value = "/users/{userid}/add-friend")
    public String processAddFriend(@PathVariable("userid") String id,
            @RequestParam("dest-username") String destUsername) {

        // creating owner, user, and authority

        String enviaUsername = "";
        User recibe = userService.findUser(destUsername).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) authentication
                        .getPrincipal();

                enviaUsername = currentUser.getUsername();

            }
        }
        // si no encuentra al usuario
        if (!userService.findAll().contains(userService.findUser(destUsername).get())) {
            return "redirect:/users/" + enviaUsername;
        }

        // amiga que ya esta
        if (invitacionAmistadService.findFriendsUser(userService.findUser(enviaUsername).get())
                .contains(destUsername)) {

            return "redirect:/users/" + enviaUsername;
        }
        InvitacionAmistad invitacionAmistad = new InvitacionAmistad();
        invitacionAmistad.setUserenvia(userService.findUser(enviaUsername).get());
        invitacionAmistad.setUserrecibe(recibe);
        invitacionAmistad.setCreatedAt(new Date());
        invitacionAmistadService.saveInvitacionAmistad(invitacionAmistad);

        return "redirect:/users/" + enviaUsername;

    }
}
