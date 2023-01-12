package org.springframework.samples.dwarf.tablero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import javax.persistence.Table;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.samples.dwarf.carta.Carta;
import org.springframework.samples.dwarf.carta.TipoCarta;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.user.EstadisticaService;
import org.springframework.samples.dwarf.user.InvitacionAmistadService;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/partida")
public class TableroController {

    private String tablero = "tablero/tablero";
    private String tablero1 = "tablero/Showtablerocopy";
    private String eleccionMateriales = "tablero/eleccionMateriales";
    private String eleccionCartas = "tablero/eleccionCartas";
    private String showListaPartidas = "tablero/showListaPartidas";

    private TableroService taservice;
    private JugadorService jugadorService;
    private EstadisticaService estadisticaService;
    private UserService userService;
    private InvitacionAmistadService invitacionAmistadService;

    @Autowired
    public TableroController(TableroService service, JugadorService jugadorService,
            EstadisticaService estadisticaService, UserService userService,
            InvitacionAmistadService invitacionAmistadService) {
        this.taservice = service;
        this.jugadorService = jugadorService;
        this.estadisticaService = estadisticaService;
        this.userService = userService;
        this.invitacionAmistadService = invitacionAmistadService;
    }

    @GetMapping("/")
    public String showTablero(Map<String, Object> model) {
        Tablero tabla = new Tablero();
        model.put("tablero", tabla);
        return tablero;
    }

    @PostMapping("/")
    public String processTablero(
            @RequestParam String name, @RequestParam(required = false) String username1,
            @RequestParam(required = false) String username2,
            @RequestParam(required = false) String username3) {

        Tablero tabla = taservice.saveTableroFromProcess(name, username1, username2, username3);

        return "redirect:/partida/" + tabla.getId() + "/comienza";

    }

    @GetMapping("/all")
    public String showPartidasList(Model model) {

        List<Tablero> partidas = taservice.findAllFinished();

        model.addAttribute("partidas", partidas);
        model.addAttribute("terminadas", true);

        return showListaPartidas;
    }

    @GetMapping("/en-curso")
    public String showPartidasEnCurso(Model model, HttpServletResponse response) {
        response.addHeader("Refresh", "2");

        List<Tablero> partidas = taservice.findAllNotFinished();

        model.addAttribute("partidas", partidas);
        model.addAttribute("terminadas", false);

        return showListaPartidas;
    }

    @GetMapping("/{partidaId}")
    public String showTablero(@PathVariable("partidaId") Integer id, Model model, HttpServletResponse response) {
        /* response.addHeader("Refresh", "3"); */

        Tablero table = taservice.findById(id);

        if (!taservice.puedoSerEspectador(table))
            return "redirect:/";

        List<Integer> mazosConEnanoEncima = table.mazosConEnanoEncima();

        model.addAttribute("mazosConEnanoEncima", mazosConEnanoEncima);
        model.addAttribute("id_partida", table.getId());
        model.addAttribute("nombrePartida", table.getName());
        model.addAttribute("chat", table.getChat());
        model.addAttribute("partida", table);
        model.addAttribute("chatLine", new ChatLine());

        String username = table.getUsernameByTurno();

        model.addAttribute("username", username);
        model.addAttribute("tablero1", table.getSubMazos(0));
        model.addAttribute("tablero2", table.getSubMazos(1));
        model.addAttribute("tablero3", table.getSubMazos(2));
        model.addAttribute("tablero4", table.getSubMazos(3));
        model.addAttribute("jugadores", table.getJugadores());
        model.addAttribute("cartasRestantesBaraja", table.getCartasRestantesBaraja());

        model.addAttribute("asociacionesUsernameMazo", table.getAsociacionesUsernameMazo());

        // Colores enanos
        model.addAttribute("asociacionesColores", table.getAsociacionesColores());

        // Rondas
        model.addAttribute("ronda", table.getRonda());

        // Resultados finales
        if (table.getJugadores().get(0).getPosicionFinal() != null) {
            model.addAttribute("jugadoresOrdenados",
                    table.getJugadores().stream().sorted(Comparator.comparing(j -> j.getPosicionFinal())).toList());
        }

        if (table.alguienTiene4Objetos())
            return "redirect:/partida/" + id + "/fin";

        return tablero1;
    }

    @GetMapping("/{partidaId}/comienza")
    public String rondaPrincipio(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);

        taservice.sacarYColocarCartasDeBaraja(tabla);

        taservice.descolocarEnanos(tabla);

        return "redirect:/partida/" + id;
    }

    @GetMapping("/{partidaId}/coloca")
    public String rondaColoca(@PathVariable("partidaId") Integer id, Model model, @RequestParam String username,
            @RequestParam Integer posicion) {

        Tablero tabla = taservice.findById(id);
        Mazo mazoSeleccionado = tabla.getMazos().get(posicion - 1);

        if (!userService.findAuthenticatedUser().getUsername().equals(username))
            return "redirect:/partida/" + id;

        if (tabla.mazoNormalTieneEnanoEncima(posicion))
            return "redirect:/partida/" + id;

        if (mazoSeleccionado.getFirstCarta().getTipo().getName().equals("base"))
            return "redirect:/partida/" + id;

        Jugador jugadorActual = tabla.getJugadorActual(username);

        if (mazoSeleccionado.isFirstCartaTipo("ayuda")) {
            jugadorActual.setEnanosDisponibles(jugadorActual.getEnanosDisponibles() + 2);
        }

        taservice.colocarEnanos(tabla, username, posicion);

        String accion5 = tabla.getMazos().get(posicion - 1).getFirstCarta().accion5(tabla, jugadorActual);

        if (!tabla.alguienTieneEnanos())
            return "redirect:/partida/" + id + "/recursos";

        if (accion5 != null) {
            return accion5;
        }

        taservice.setSiguienteTurno(tabla, username);

        return "redirect:/partida/" + id;
    }

    @Transactional
    @GetMapping("{partidaId}/recursos")
    public String rondaRecursos(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);

        if (tabla.getMazos().get(12).getCartas().size() == 0) {
            return "redirect:/partida/" + id + "/fin";
        }

        tabla.getJugadores().stream().filter(j -> j.isTurno()).toList().get(0).setTurno(false);
        // tabla.getJugadores().get(0).setTurno(true);

        boolean farmeo2 = true;
        int accion = 1;
        farmeo2 = tabla.analizarDefensas();
        while (accion <= 5) {
            for (Jugador j : tabla.getJugadores()) {
                for (Enano e : j.getEnano()) {
                    if (e.getPosicion() != 12) {
                        Carta primera = e.getMazo().getFirstCarta();
                        if (accion == 2)
                            primera.accion2(tabla, j, e);
                        if (accion == 3 && farmeo2)
                            primera.accion3(tabla, j, e);
                        if (accion == 4)
                            primera.accion4(tabla, j, e);
                    }
                }
            }
            accion++;
        }

        tabla.getJugadores().stream().filter(j -> j.isPrimerjugador()).toList().get(0).setTurno(true);

        // Sumamos 1 ronda
        tabla.setRonda(tabla.getRonda() + 1);

        return "redirect:/partida/" + id + "/comienza";
    }

    @Transactional
    @GetMapping("{partidaId}/fin")
    public String finPartida(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);

        // Comprobamos si tenemos ganador sin desempate
        Map<String, Integer> mayorias = new HashMap<>();
        // Inicializamos mayorias a 0
        for (Jugador j : tabla.getJugadores()) {
            mayorias.put(j.getUser().getUsername(), 0);
        }

        List<Integer> oro = tabla.getJugadores().stream().map(j -> j.getOro()).toList();
        List<Integer> acero = tabla.getJugadores().stream().map(j -> j.getAcero()).toList();
        List<Integer> objetos = tabla.getJugadores().stream().map(j -> j.getObjeto()).toList();

        List<Integer> oroOrdenado = oro.stream().sorted(Comparator.reverseOrder()).toList();
        // Si hay mayoria
        if (oroOrdenado.get(0) != oroOrdenado.get(1)) {
            String user = tabla.getJugadores().get(oro.indexOf(oroOrdenado.get(0))).getUser().getUsername();
            mayorias.put(
                    user,
                    mayorias.get(user) + 1);
        }

        List<Integer> aceroOrdenado = acero.stream().sorted(Comparator.reverseOrder()).toList();
        // Si hay mayoria
        if (aceroOrdenado.get(0) != aceroOrdenado.get(1)) {
            String user = tabla.getJugadores().get(acero.indexOf(aceroOrdenado.get(0))).getUser().getUsername();
            mayorias.put(
                    user,
                    mayorias.get(user) + 1);
        }

        List<Integer> objetosOrdenado = objetos.stream().sorted(Comparator.reverseOrder()).toList();
        // Si hay mayoria
        if (objetosOrdenado.get(0) != objetosOrdenado.get(1)) {
            String user = tabla.getJugadores().get(objetos.indexOf(objetosOrdenado.get(0))).getUser().getUsername();
            mayorias.put(
                    user,
                    mayorias.get(user) + 1);
        }

        List<Jugador> results; // Orden de victoria
        Comparator<Jugador> comparator = Comparator.comparing(j -> mayorias.get(j.getUser().getUsername()),
                Comparator.reverseOrder());
        comparator = comparator.thenComparing(Comparator.comparing(jugador -> jugador.getMedalla(),
                Comparator.reverseOrder()));
        comparator = comparator.thenComparing(Comparator.comparing(jugador -> jugador.getHierro(),
                Comparator.reverseOrder()));

        results = tabla.getJugadores().stream().sorted(comparator).toList();

        // Guardamos las posiciones finales
        for (int i = 0; i < results.size(); i++) {
            results.get(i).setPosicionFinal(i + 1);
        }

        // Seteamos "terminada" y "finishedAt"
        tabla.setTerminada(true);
        tabla.setFinishedAt(new Date());

        // Actualizamos estadistica de los usuarios
        estadisticaService.actualizarEstadistica(tabla);

        return "redirect:/partida/" + id;
    }

    @GetMapping("{partidaId}/chatline")
    @ResponseBody
    public String getChatLines(@PathVariable("partidaId") Integer id, ChatLine chatLine,
            BindingResult result) {

        Tablero tabla = taservice.findById(id);
        return tabla.getChatLinesInJSON();
    }

    @Transactional
    @PostMapping("{partidaId}/chatline")
    public String processChatLine(@PathVariable("partidaId") Integer id, ChatLine chatLine,
            BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("#".repeat(200));
            return "redirect:/partida/" + id;

        } else {
            Tablero tabla = taservice.findById(id);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                if (authentication.isAuthenticated()) {
                    User currentUser = (User) authentication.getPrincipal();
                    chatLine.setUsername(currentUser.getUsername());
                } else {
                    chatLine.setUsername("anonymous");
                }
            }

            tabla.getChat().add(chatLine);
            return "redirect:/partida/" + id;
        }
    }

    @Transactional
    @GetMapping("{partidaId}/eleccion-materiales")
    public String eleccionMateriales(@PathVariable("partidaId") Integer id, Model model,
            @RequestParam("username") String username) {

        model.addAttribute("username", username);
        model.addAttribute("id_partida", id);
        return eleccionMateriales;
    }

    @Transactional
    @GetMapping("{partidaId}/eleccion-material")
    public String eleccionMaterial(@PathVariable("partidaId") Integer id, @RequestParam("material") String material,
            @RequestParam("username") String username) {

        Tablero tablero = taservice.findById(id);
        Jugador j = tablero.getJugadorByUsername(username);

        if (material.equals("oro"))
            j.setOro(j.getOro() + 5);
        if (material.equals("hierro"))
            j.setHierro(j.getHierro() + 5);
        if (material.equals("acero"))
            j.setAcero(j.getAcero() + 5);

        Jugador jugadorActual = tablero.getJugadores().stream()
                .filter(jugador -> jugador.getUser().getUsername().equals(username))
                .toList().get(0);
        int indexOfJugadorActual = tablero.getJugadores().indexOf(jugadorActual);

        int i = indexOfJugadorActual + 1;
        while (true) {

            if (i == tablero.getJugadores().size()) {
                i = 0;
                continue;
            }

            if (tablero.getJugadores().get(i).getEnanosDisponibles() > 0) {
                tablero.setTurnoByUsername(tablero.getJugadores().get(i).getUser().getUsername());
                break;
            }

            i++;
        }

        return "redirect:/partida/" + id;
    }

    @Transactional
    @GetMapping("{partidaId}/eleccion-cartas")
    public String eleccionCartas(@PathVariable("partidaId") Integer id, Model model,
            @RequestParam("username") String username) {
        Tablero tablero = taservice.findById(id);
        List<Carta> cartasTotal = taservice.findAllCartas();
        model.addAttribute("username", username);
        model.addAttribute("id_partida", id);
        for (Mazo mazo : tablero.getMazos()) {
            if (mazo.getPosicion() > 9) {
                if (mazo.getCartas().contains(taservice.findCartaById(58))) {
                    mazo.getCartas().remove(taservice.findCartaById(58));
                }
                cartasTotal.removeAll(mazo.getCartas());
            } else {

                cartasTotal.remove(mazo.getFirstCarta());
            }
        }

        model.addAttribute("cartas", cartasTotal);
        return eleccionCartas;
    }

    @Transactional
    @GetMapping("{partidaId}/eleccion-carta")
    public String eleccionCarta(@PathVariable("partidaId") Integer id, @RequestParam("carta") Integer carta,
            @RequestParam("username") String username) {

        Tablero tablero = taservice.findById(id);

        Carta cartaElegida = taservice.findCartaById(carta);

        Mazo mazoElegido = tablero.getMazos().stream().filter(m -> m.getPosicion().equals(cartaElegida.getPosicion()))
                .toList()
                .get(0);
        mazoElegido.getCartas().remove(cartaElegida);
        mazoElegido.getCartas().add(0, cartaElegida);

        Jugador jugadorActual = tablero.getJugadores().stream()
                .filter(jugador -> jugador.getUser().getUsername().equals(username))
                .toList().get(0);
        int indexOfJugadorActual = tablero.getJugadores().indexOf(jugadorActual);

        int i = indexOfJugadorActual + 1;
        while (true) {

            if (i == tablero.getJugadores().size()) {
                i = 0;
                continue;
            }

            if (tablero.getJugadores().get(i).getEnanosDisponibles() > 0) {
                tablero.setTurnoByUsername(tablero.getJugadores().get(i).getUser().getUsername());
                break;
            }

            i++;
        }

        return "redirect:/partida/" + id;
    }

}
