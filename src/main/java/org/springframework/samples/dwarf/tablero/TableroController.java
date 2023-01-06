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
    private String showListaPartidas = "tablero/showListaPartidas";

    private TableroService taservice;
    private JugadorService jugadorService;

    @Autowired
    public TableroController(TableroService service, JugadorService jugadorService) {
        this.taservice = service;
        this.jugadorService = jugadorService;
    }

    @Transactional
    @GetMapping("/")
    public String showTablero(Map<String, Object> model) {
        Tablero tabla = new Tablero();
        model.put("tablero", tabla);
        return tablero;
    }

    @Transactional
    @PostMapping("/")
    public String processTablero(
            @RequestParam String name, @RequestParam(required = false) String username1,
            @RequestParam(required = false) String username2,
            @RequestParam(required = false) String username3) {

        Tablero tabla = new Tablero();
        tabla.setName(name);

        List<Mazo> mazos = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            if (i <= 9) {
                Carta carta = taservice.findCartaById(i);
                List<Carta> cartas = new ArrayList<>();
                Mazo mazo = new Mazo();
                cartas.add(carta);
                mazo.setPosicion(i);
                mazo.setCartas(cartas);
                mazos.add(mazo);
            } else if (i < 13) {
                List<Carta> cartasEspeciales = taservice.findByPosicion(i);
                Mazo mazo = new Mazo();
                mazo.setCartas(cartasEspeciales);
                mazo.setPosicion(i);
                mazos.add(mazo);
            } else {
                Mazo mazo = new Mazo();
                List<Carta> cartas = new ArrayList<>();

                for (int j = 10; j < 55; j++) {
                    Carta carta = new Carta();
                    carta = taservice.findCartaById(j);
                    cartas.add(carta);
                }
                mazo.setPosicion(i);
                mazo.setCartas(cartas);
                mazos.add(mazo);
            }
        }

        tabla.setRonda(1);
        tabla.setMazos(mazos);
        tabla.setTerminada(false);
        List<Jugador> jugadores = new ArrayList<>();
        if (username1 != null) {
            Jugador j = jugadorService.createJugadorByUsername(username1, true);
            jugadores.add(j);
        }
        if (username2 != null) {
            Jugador j = jugadorService.createJugadorByUsername(username2, false);
            jugadores.add(j);
        }
        if (username3 != null) {
            Jugador j = jugadorService.createJugadorByUsername(username3, false);
            jugadores.add(j);
        }

        // tabla.setJugadores(jugadorService.findAll());

        tabla.setJugadores(jugadores);

        // Seteamos los turnos iniciales
        for (int i = 0; i < tabla.getJugadores().size(); i++) {
            if (i == 0) {
                tabla.getJugadores().get(i).setTurno(true);
            } else {
                tabla.getJugadores().get(i).setTurno(false);
            }
        }

        tabla.setDefensaTotal(false);
        tabla.setCreatedAt(new Date());

        taservice.saveTablero(tabla);

        return "redirect:/partida/" + tabla.getId() + "/comienza";

    }

    @GetMapping("/all")
    public String showPartidasList(Model model) {
        List<Tablero> partidas = taservice.findAll();
        List<Tablero> result = new ArrayList<>();
        for (Tablero tabla : partidas) {
            if (tabla.isTerminada()) {
                result.add(tabla);
            }
        }

        model.addAttribute("partidas", result);
        model.addAttribute("terminadas", true);

        return showListaPartidas;
    }

    @GetMapping("/en-curso")
    public String showPartidasEnCurso(Model model, HttpServletResponse response) {
        response.addHeader("Refresh", "2");
        List<Tablero> partidas = taservice.findAll();
        List<Tablero> result = new ArrayList<>();
        for (Tablero tabla : partidas) {
            if (!tabla.isTerminada()) {
                result.add(tabla);
            }
        }
        model.addAttribute("partidas", result);
        model.addAttribute("terminadas", false);

        return showListaPartidas;
    }

    @Transactional
    @GetMapping("/{partidaId}")
    public String showTablero(@PathVariable("partidaId") Integer id, Model model, HttpServletResponse response) {
        /* response.addHeader("Refresh", "3"); */
        Tablero table = taservice.findById(id);
        List<Mazo> mazo = table.getMazos();
        List<Mazo> mazo1 = mazo.subList(0, 3);
        List<Mazo> mazo2 = mazo.subList(3, 6);
        List<Mazo> mazo3 = mazo.subList(6, 9);
        List<Mazo> mazo4 = mazo.subList(9, 12);

        // PROVISIONAL: ENANO EN MAZO
        List<Enano> todosLosEnanos = new ArrayList<>();
        for (Jugador j : table.getJugadores()) {
            for (Enano e : j.getEnano()) {
                todosLosEnanos.add(e);
            }
        }
        List<Integer> mazosConEnanoEncima = todosLosEnanos.stream().filter(e -> e.getMazo() != null)
                .map(e -> e.getMazo().getId()).toList();

        model.addAttribute("mazosConEnanoEncima", mazosConEnanoEncima);
        model.addAttribute("id_partida", table.getId());
        model.addAttribute("nombrePartida", table.getName());
        model.addAttribute("chat", table.getChat());
        model.addAttribute("partida", table);
        model.addAttribute("chatLine", new ChatLine());
        // ==============================

        String username = table.getJugadores().stream().filter(j -> j.isTurno()).toList().get(0).getUser()
                .getUsername();
        model.addAttribute("username", username);
        model.addAttribute("tablero1", mazo1);
        model.addAttribute("tablero2", mazo2);
        model.addAttribute("tablero3", mazo3);
        model.addAttribute("tablero4", mazo4);
        model.addAttribute("jugadores", table.getJugadores());
        model.addAttribute("cartasRestantesBaraja",
                table.getMazos().get(table.getMazos().size() - 1).getCartas().size());

        Map<Integer, String> asociacionesUsernameMazo = new HashMap<>(); // <idMazo, String>
        for (Jugador j : table.getJugadores()) {
            String username1 = j.getUser().getUsername();
            for (Enano e : j.getEnano()) {
                if (e.getMazo() != null) {
                    asociacionesUsernameMazo.put(e.getMazo().getId(), username1);
                }
            }
        }
        model.addAttribute("asociacionesUsernameMazo", asociacionesUsernameMazo);

        System.out.println(asociacionesUsernameMazo.toString());

        // Colores enanos
        List<String> colores = Arrays.asList("Rojo", "Azul", "Amarillo");
        Map<String, String> asociacionesColores = new HashMap<>(); // <username, color>
        for (int i = 0; i < table.getJugadores().size(); i++) {
            String username1 = table.getJugadores().get(i).getUser().getUsername();
            asociacionesColores.put(username1, colores.get(i));
        }
        model.addAttribute("asociacionesColores", asociacionesColores);

        // Rondas
        model.addAttribute("ronda", table.getRonda());

        // Resultados finales
        if (table.getJugadores().get(0).getPosicionFinal() != null) {
            model.addAttribute("jugadoresOrdenados",
                    table.getJugadores().stream().sorted(Comparator.comparing(j -> j.getPosicionFinal())).toList());
        }

        if (table.getJugadores().get(0).getPosicionFinal() == null) {
            for (Jugador j : table.getJugadores()) {
                if (j.getObjeto() >= 4) {
                    return "redirect:/partida/" + id + "/fin";
                }
            }
            if (table.getMazos().get(12).getCartas().size() == 0) {
                return "redirect:/partida/" + id + "/fin";
            }
        }
        /*
         * Codigo para cronometro
         * while (table.getJugadores().stream().map(j ->
         * j.getPosicionFinal()).anyMatch(p -> p == null)) {
         * table.actualizaCrono();
         * }
         */
        return tablero1;

    }

    @Transactional
    @GetMapping("/{partidaId}/comienza")
    public String rondaPrincipio(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);

        // Coger dos cartas aleatorias de la baraja y colocarlas
        List<Carta> baraja = tabla.getMazos().get(tabla.getMazos().size() - 1).getCartas();

        /*
         * Generar valor aleatorio ejemplo:
         * int HASTA = 20;
         * int DESDE = 7;
         * int aleatorio = (int)(Math.random()*(HASTA-DESDE+1)+DESDE);
         */

        for (int i = 0; i < 2; i++) {
            int DESDE = 0;
            int HASTA = baraja.size() - 1;

            int indexCarta = (int) (Math.random() * (HASTA - DESDE + 1) + DESDE);
            Carta carta = tabla.getMazos().get(12).getCartas().get(indexCarta);
            tabla.getMazos().stream().filter(mazo -> mazo.getPosicion() == carta.getPosicion()).toList().get(0)
                    .getCartas()
                    .add(0, carta);
            baraja.remove(carta);

            if (baraja.size() == 0)
                break;
        }

        List<Jugador> jugadores = tabla.getJugadores();
        for (Jugador j : jugadores) {
            List<Enano> enanos = new ArrayList<>();
            for (int r = 0; r < 4; r++) {
                Enano enano = new Enano();
                enano.setPosicion(12);
                enanos.add(enano);
            }
            j.setEnano(enanos);

        }

        // Se establecen los enanos disponibles por defecto a 2
        tabla.getJugadores().stream().forEach(jugador -> jugador.setEnanosDisponibles(2));

        // Forzamos una carta de ayuda al inicio para testing
        // tabla.getMazos().get(1).getCartas().add(0,
        // tabla.getMazos().get(12).getCartas().stream()
        // .filter(cartas -> cartas.getId() == 24 || cartas.getId() ==
        // 32).toList().get(0));

        return "redirect:/partida/" + id;
    }

    @Transactional
    @GetMapping("/{partidaId}/coloca")
    public String rondaColoca(@PathVariable("partidaId") Integer id, Model model, @RequestParam String username,
            @RequestParam Integer posicion) {

        Tablero tabla = taservice.findById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                User currentUser = (User) authentication.getPrincipal();
                if (!username.equals(currentUser.getUsername()) && false) { // Cambiar para usar autenticacion
                    System.out.println("#".repeat(200));
                    return "redirect:/partida/" + id;
                }

                // Si el mazo ya tiene un enano encima no se coloca
                if (tabla.tieneEnanoEncima(tabla.getMazos().get(posicion - 1).getFirstCarta().getId()) && posicion >= 1
                        && posicion <= 9)
                    return "redirect:/partida/" + id;

                if (tabla.getMazos().get(posicion - 1).getFirstCarta().getTipo().getName().equals("base"))
                    return "redirect:/partida/" + id;

                Jugador jugadorActual = tabla.getJugadores().stream()
                        .filter(jugador -> jugador.getUser().getUsername().equals(username))
                        .toList().get(0);

                if (tabla.getMazos().get(posicion - 1).getFirstCarta().getTipo().getName().equals("ayuda")) {
                    // jugador con username igual al query

                    jugadorActual.setEnanosDisponibles(jugadorActual.getEnanosDisponibles() + 2);
                }
                List<Enano> enanosJugador = tabla.getJugadores().stream()
                        .filter(jugador -> jugador.getUser().getUsername().equals(username))
                        .toList().get(0).getEnano();
                // Colocar enanos sobre todo el tablero (para las cartas especiales y no
                // especiales)

                for (Enano e : enanosJugador) {
                    if (e.getPosicion() == 12) {
                        e.setPosicion(posicion);
                        for (Mazo mazo : tabla.getMazos()) {
                            if (10 <= mazo.getPosicion() && 13 > mazo.getPosicion() && enanosJugador.size() >= 1
                                    && tabla.getJugadores().stream()
                                            .filter(jugador -> jugador.getUser().getUsername().equals(username))
                                            .map(j -> j.getObjeto()).toList().get(0) >= 4
                                    && mazo.getPosicion() == posicion) {
                                e.setMazo(mazo);
                                jugadorActual.setEnanosDisponibles(jugadorActual.getEnanosDisponibles() - 1);

                                break;
                            } else if (10 <= mazo.getPosicion() && 13 > mazo.getPosicion() && enanosJugador.size() >= 2
                                    && mazo.getPosicion() == posicion) {
                                e.setMazo(mazo);
                                jugadorActual.setEnanosDisponibles(jugadorActual.getEnanosDisponibles() - 2);
                                break;

                            } else if (!(10 <= mazo.getPosicion() && 13 > mazo.getPosicion())
                                    && posicion == mazo.getPosicion()) {
                                e.setMazo(mazo);
                                jugadorActual.setEnanosDisponibles(jugadorActual.getEnanosDisponibles() - 1);

                                break;

                            }
                        }
                        break;
                    }
                }

                String accion5 = tabla.getMazos().get(posicion - 1).getFirstCarta().accion5(tabla, jugadorActual);

                if (!tabla.getJugadores().stream().anyMatch(jugador -> jugador.getEnanosDisponibles() > 0))
                    return "redirect:/partida/" + id + "/recursos";

                // Seteamos turno en el siguiente jugador con enanos disponibles
                int indexOfJugadorActual = tabla.getJugadores().indexOf(jugadorActual);

                int i = indexOfJugadorActual + 1;
                while (true) {

                    if (i == tabla.getJugadores().size()) {
                        i = 0;
                        continue;
                    }

                    if (tabla.getJugadores().get(i).getEnanosDisponibles() > 0) {
                        tabla.setTurnoByUsername(tabla.getJugadores().get(i).getUser().getUsername());
                        break;
                    }

                    i++;
                }

                // Nueva condicion de llamada a recursos
                // si enanosDisponibles de todos igual a 0

                if (accion5 != null) {
                    return accion5;
                }

                return "redirect:/partida/" + id;

            }
        }
        return "redirect:/login/";

    }

    @Transactional
    @GetMapping("{partidaId}/recursos")
    public String rondaRecursos(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);

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
        tabla.setTerminada(true);

        return "redirect:/partida/" + id;
    }

    @Transactional
    @GetMapping("{partidaId}/borrar_partida")
    public String borrarPartida(@PathVariable("partidaId") Integer id) {
        // Acaba cuando un jugador tiene 4 objetos o la baraja no tiene mas cartas

        return "redirect:/";
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

        return "redirect:/partida/" + id;
    }
}
