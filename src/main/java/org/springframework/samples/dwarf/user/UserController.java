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
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.logro.Logro;
import org.springframework.samples.dwarf.logro.LogroService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

    private static final String VIEWS_OWNER_CREATE_FORM = "users/createOwnerForm";
    private static final String view_user = "users/showUser";
    private static final String VIEW_USERS_LIST = "users/usersList";

    private final JugadorService ownerService;
    private final UserService userService;
    private final LogroService logroService;

    @Autowired
    public UserController(JugadorService clinicService, UserService userService, LogroService logroService) {
        this.ownerService = clinicService;
        this.userService = userService;
        this.logroService = logroService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping(value = "/users")
    public String usersList(Map<String, Object> model) {
        List<User> usuarios = userService.findAll();
        model.put("usuarios", usuarios);
        return VIEW_USERS_LIST;
    }

    @GetMapping(value = "/users/find")
    public String initCreationForm(Map<String, Object> model) {
        User user = new User();
        model.put("user", user);
        return "users/findUsers";
    }

    @PostMapping(value = "/users/find")
    public String processCreationForm(Map<String, Object> model, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/findUsers";
        } else {
            // creating owner, user, and authority
            model.put("usuarios", userService.findUserByString(user.username));
            return VIEW_USERS_LIST;
        }
    }

    @GetMapping(value = "/users/{userid}")
    public String showUser(@PathVariable("userid") String id, Map<String, Object> model) {
        User usuario = userService.findUser(id).get();
        List<Jugador> jugadores = ownerService.findJugadorUser(id);

        List<Logro> logrosAll = logroService.findAll();
        List<Logro> logrosCumplidos = new ArrayList<>();

        for (Logro logro : logrosAll) {
            if (logro.getTipo().getName().equals("partidas_ganadas")) {
                if (usuario.getEstadistica().getPartidasGanadas() >= logro.getRequisito())
                    logrosCumplidos.add(logro);
            }
            if (logro.getTipo().getName().equals("recursos_conseguidos")) {

                Integer hierro = 0;
                Integer acero = 0;
                Integer objetos = 0;
                Integer medallas = 0;
                Integer oro = 0;
                for (Jugador j : jugadores) {
                    hierro += j.getHierro();
                    acero += j.getAcero();
                    objetos += j.getObjeto();
                    medallas += j.getMedalla();
                    oro += j.getOro();
                }

                if (logro.getName().contains("hierro") && hierro >= logro.getRequisito())
                    logrosCumplidos.add(logro);
                if (logro.getName().contains("acero") && acero >= logro.getRequisito())
                    logrosCumplidos.add(logro);
                if (logro.getName().contains("objetos") && objetos >= logro.getRequisito())
                    logrosCumplidos.add(logro);
                if (logro.getName().contains("medallas") && medallas >= logro.getRequisito())
                    logrosCumplidos.add(logro);
                if (logro.getName().contains("oro") && oro >= logro.getRequisito())
                    logrosCumplidos.add(logro);
            }

        }

        model.put("usuario", usuario);
        model.put("jugadores", jugadores);
        model.put("logros", logrosCumplidos);
        return view_user;
    }

}
