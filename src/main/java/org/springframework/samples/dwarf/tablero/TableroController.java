package org.springframework.samples.dwarf.tablero;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.lobby.Lobby;
import org.springframework.samples.dwarf.lobby.LobbyService;
import org.springframework.samples.dwarf.user.EstadisticaService;
import org.springframework.samples.dwarf.user.InvitacionAmistadService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
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
    private LobbyService lobbyService;

    @Autowired
    public TableroController(TableroService service, JugadorService jugadorService,
            EstadisticaService estadisticaService, UserService userService,
            InvitacionAmistadService invitacionAmistadService, LobbyService lobbyService) {
        this.taservice = service;
        this.jugadorService = jugadorService;
        this.estadisticaService = estadisticaService;
        this.userService = userService;
        this.invitacionAmistadService = invitacionAmistadService;
        this.lobbyService = lobbyService;
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
            @RequestParam(required = false) String username3, @RequestParam("lobby-id") Integer lobbyId) {

        Lobby lobby = lobbyService.findById(lobbyId);

        lobbyService.setTableroId(lobby, lobbyId);
        List<Jugador> jugadores = jugadorService.creacionLista(username1, username2, username3);
        Tablero tabla = taservice.saveTableroFromProcess(name, username1, username2, username3, jugadores);

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

        Tablero table = taservice.findById(id);
        User authenticatedUser = userService.findAuthenticatedUser();

        if (!taservice.isMyTurno(table, authenticatedUser))
            response.addHeader("Refresh", "3");

        if (!taservice.puedoSerEspectador(table, authenticatedUser))
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

        if (mazoSeleccionado.isFirstCartaTipo("ayuda")) {
            taservice.incrementarEnanosPorAyuda(tabla, username);
        }

        taservice.colocarEnanos(tabla, username, posicion);

        String accion5 = taservice.ejecutarAccionEnColoca(tabla, posicion, username);

        if (!tabla.alguienTieneEnanos())
            return "redirect:/partida/" + id + "/recursos";

        if (accion5 != null) {
            return accion5;
        }

        taservice.setSiguienteTurno(tabla, username);

        return "redirect:/partida/" + id;
    }

    @GetMapping("{partidaId}/recursos")
    public String rondaRecursos(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);

        if (!tabla.barajaTieneCartas()) {
            return "redirect:/partida/" + id + "/fin";
        }

        taservice.quitarTurnoActual(tabla);

        taservice.ejecutarAccionesCartasEnRecursos(tabla);

        taservice.setTurnoToPrimerJugador(tabla);

        taservice.incrementarRonda(tabla);

        return "redirect:/partida/" + id + "/comienza";
    }

    @GetMapping("{partidaId}/fin")
    public String finPartida(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);

        taservice.calcularPosicionesFinales(tabla);

        estadisticaService.actualizarEstadistica(tabla);

        Lobby lobby = lobbyService.findByTableroId(id);
        lobbyService.setTableroId(lobby, null);

        return "redirect:/partida/" + id;
    }

    @GetMapping("{partidaId}/chatline")
    @ResponseBody
    public String getChatLines(@PathVariable("partidaId") Integer id, ChatLine chatLine,
            BindingResult result) {

        Tablero tabla = taservice.findById(id);
        return tabla.getChatLinesInJSON();
    }

    @PostMapping("{partidaId}/chatline")
    public String processChatLine(@PathVariable("partidaId") Integer id, ChatLine chatLine,
            BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/partida/" + id;
        }
        Tablero tabla = taservice.findById(id);

        User currentUser = userService.findAuthenticatedUser();

        taservice.addMessage(tabla, currentUser, chatLine.getMensaje());
        return "redirect:/partida/" + id;

    }

    @GetMapping("{partidaId}/eleccion-materiales")
    public String eleccionMateriales(@PathVariable("partidaId") Integer id, Model model,
            @RequestParam("username") String username) {

        model.addAttribute("username", username);
        model.addAttribute("id_partida", id);
        return eleccionMateriales;
    }

    @GetMapping("{partidaId}/eleccion-material")
    public String eleccionMaterial(@PathVariable("partidaId") Integer id, @RequestParam("material") String material,
            @RequestParam("username") String username) {

        Tablero tablero = taservice.findById(id);

        taservice.setMaterialesDeEleccion(tablero, username, material);

        taservice.setSiguienteTurno(tablero, username);

        return "redirect:/partida/" + id;
    }

    @GetMapping("{partidaId}/eleccion-cartas")
    public String eleccionCartas(@PathVariable("partidaId") Integer id, Model model,
            @RequestParam("username") String username) {
        Tablero tablero = taservice.findById(id);

        model.addAttribute("username", username);
        model.addAttribute("id_partida", id);

        model.addAttribute("cartas", taservice.setCartasParaEleccion(tablero));
        return eleccionCartas;
    }

    @GetMapping("{partidaId}/eleccion-carta")
    public String eleccionCarta(@PathVariable("partidaId") Integer id, @RequestParam("carta") Integer carta,
            @RequestParam("username") String username) {

        Tablero tablero = taservice.findById(id);

        taservice.moverCartaAlPrincipioDelMazo(tablero, carta);

        taservice.setSiguienteTurno(tablero, username);

        return "redirect:/partida/" + id;
    }

}
