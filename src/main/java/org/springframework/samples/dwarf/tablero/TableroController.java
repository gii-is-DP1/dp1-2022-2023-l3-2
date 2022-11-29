package org.springframework.samples.dwarf.tablero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/partida")
public class TableroController {

    private String tablero = "tablero/tablero";
    private String tablero1 = "tablero/Showtablerocopy";

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
    public String processTablero(@Valid Tablero tabla, BindingResult result) {
        if (result.hasErrors()) {
            return tablero;
        } else {
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
            tabla.setJugadores(jugadorService.findAll());

            // Seteamos los turnos iniciales
            for (int i = 0; i < tabla.getJugadores().size(); i++) {
                if (i == 0) {
                    tabla.getJugadores().get(i).setTurno(true);
                } else {
                    tabla.getJugadores().get(i).setTurno(false);
                }
            }

            taservice.saveTablero(tabla);

            return "redirect:/partida/" + tabla.getId() + "/comienza";
        }
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
                if (j.getObjeto() == 4) {
                    return "redirect:/partida/" + id + "/fin";
                }
            }
            if (table.getMazos().get(12).getCartas().size() == 0) {
                return "redirect:/partida/" + id + "/fin";
            }
        }
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

                Jugador jugadorActual = tabla.getJugadores().stream()
                        .filter(jugador -> jugador.getUser().getUsername().equals(username))
                        .toList().get(0);

                if (tabla.getMazos().get(posicion - 1).getFirstCarta().getTipo().getName().equals("ayuda")) {
                    // jugador con username igual al query

                    jugadorActual.setEnanosDisponibles(jugadorActual.getEnanosDisponibles() + 2);
                }

                // Colocamos el primer enano disponible del jugador en la posicion
                List<Enano> enanosJugador = tabla.getJugadores().stream()
                        .filter(jugador -> jugador.getUser().getUsername().equals(username))
                        .toList().get(0).getEnano();
                for (Enano e : enanosJugador) {
                    if (e.getPosicion() == 12) {
                        e.setPosicion(posicion);
                        for (Mazo m : tabla.getMazos()) {
                            if (posicion == m.getPosicion())
                                e.setMazo(m);
                        }

                        jugadorActual.setEnanosDisponibles(jugadorActual.getEnanosDisponibles() - 1);

                        break;

                    }
                }

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

                return "redirect:/partida/" + id;

            }
        }
        return "redirect:/login/";

    }

    @Transactional
    @GetMapping("{partidaId}/recursos")
    public String rondaRecursos(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);
        /*
         * Carta primera = tabla.getJugadores().stream().filter(jugador ->
         * jugador.getEnano())
         */
        tabla.getJugadores().stream().filter(j -> j.isTurno()).toList().get(0).setTurno(false);
        tabla.getJugadores().get(0).setTurno(true);

        // Manejo cartas no comunes

        boolean farmeo = true;

        final List<Integer> ORCOS = Arrays.asList(11, 20, 30, 49);
        if (ORCOS.stream().anyMatch(cartaId -> tabla.estaEnTablero(cartaId) && !tabla.tieneEnanoEncima(cartaId))) {
            farmeo = false;
        }

        // ========================

        final List<Integer> ALLOY_STEEL = Arrays.asList(10, 15, 19, 21, 23, 29, 44, 54);
        for (Jugador j : tabla.getJugadores()) {
            for (Enano e : j.getEnano()) {
                if (e.getPosicion() != 12) {
                    Carta primera = e.getMazo().getFirstCarta();
                    if (primera.getTipo().getName().equals("extraccion")) {

                        if (primera.getDevuelve() != null || primera.getEntrada() != null) {

                            if (primera.getDevuelve().equals("hierro") && farmeo) {
                                j.setHierro(j.getHierro() + primera.getCantidaddevuelve());
                            }
                            if (primera.getDevuelve().equals("oro") && farmeo) {
                                j.setOro(j.getOro() + primera.getCantidaddevuelve());
                            }
                            if (primera.getDevuelve().equals("medalla") && farmeo) {
                                j.setMedalla(j.getMedalla() + primera.getCantidaddevuelve());
                            }
                            if (primera.getDevuelve().equals("acero") && farmeo) {
                                j.setAcero(j.getAcero() + primera.getCantidaddevuelve());
                            }
                            if (primera.getDevuelve().equals("objeto") && farmeo) {
                                j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
                            }
                        }

                        if (ALLOY_STEEL.contains(primera.getId())) {
                            if (j.getHierro() >= 3) {
                                j.setHierro(j.getHierro() - 3);
                                j.setAcero(j.getAcero() + 2);
                            }
                        }
                        e.setPosicion(12);
                        e.setMazo(null);
                    }
                }
            }
        }
        for (Jugador j : tabla.getJugadores()) {
            for (Enano e : j.getEnano()) {
                if (e.getPosicion() != 12) {
                    Carta primera = e.getMazo().getFirstCarta();
                    if (primera.getTipo().getName().equals("extraccion")) {

                        if (primera.getDevuelve().equals("hierro") && farmeo) {
                            j.setHierro(j.getHierro() + primera.getCantidaddevuelve());
                        }
                        if (primera.getDevuelve().equals("oro") && farmeo) {
                            j.setOro(j.getOro() + primera.getCantidaddevuelve());
                        }
                        if (primera.getDevuelve().equals("medalla") && farmeo) {
                            j.setMedalla(j.getMedalla() + primera.getCantidaddevuelve());
                        }
                        if (primera.getDevuelve().equals("acero") && farmeo) {
                            j.setAcero(j.getAcero() + primera.getCantidaddevuelve());
                        }
                        if (primera.getDevuelve().equals("objeto") && farmeo) {
                            j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
                        }
                        e.setPosicion(12);
                        e.setMazo(null);
                    }
                }
            }
        }
        for (Jugador j : tabla.getJugadores()) {
            for (Enano e : j.getEnano()) {
                if (e.getPosicion() != 12) {
                    Carta primera = e.getMazo().getFirstCarta();
                    List<Integer> cartasForjaEspeciales = Arrays.asList(16, 25, 26, 45, 51);
                    // Condiciones especiales para las cartas de forja que piden materiales ditintos
                    if (cartasForjaEspeciales.contains(primera.getId())) {
                        if (primera.getId() == 16 && j.getAcero() >= 2 && j.getOro() >= 1) {
                            j.setAcero(j.getAcero() - 2);
                            j.setOro(j.getOro() - 1);
                            j.setObjeto(j.getObjeto() + 1);
                            break;
                        }
                        if (primera.getId() == 25 && j.getAcero() >= 1 && j.getOro() >= 1 && j.getHierro() >= 1) {
                            j.setAcero(j.getAcero() - 1);
                            j.setOro(j.getOro() - 1);
                            j.setHierro(j.getHierro() - 1);
                            j.setObjeto(j.getObjeto() + 1);
                            break;
                        }
                        if (primera.getId() == 26 && j.getAcero() >= 1 && j.getOro() >= 2) {
                            j.setAcero(j.getAcero() - 1);
                            j.setOro(j.getOro() - 2);
                            j.setObjeto(j.getObjeto() + 1);
                            break;
                        }
                        if (primera.getId() == 45 && j.getAcero() >= 2 && j.getHierro() >= 1) {
                            j.setHierro(j.getHierro() - 1);
                            j.setAcero(j.getAcero() - 2);
                            j.setObjeto(j.getObjeto() + 1);
                            break;
                        }
                        if (primera.getId() == 51 && j.getAcero() >= 2 && j.getOro() >= 1) {
                            j.setAcero(j.getAcero() - 2);
                            j.setOro(j.getOro() - 1);
                            j.setObjeto(j.getObjeto() + 1);
                            break;
                        }
                    } else if (primera.getTipo().getName().equals("forja")) {

                        if (primera.getEntrada().equals("hierro") && primera.getCantidadentrada() <= j.getHierro()) {
                            j.setHierro(j.getHierro() - primera.getCantidadentrada());
                            j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
                        }
                        if (primera.getEntrada().equals("oro") && primera.getCantidadentrada() <= j.getOro()) {
                            j.setOro(j.getOro() - primera.getCantidadentrada());
                            j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
                        }
                        if (primera.getEntrada().equals("medalla") && primera.getCantidadentrada() <= j.getMedalla()) {
                            j.setMedalla(j.getMedalla() - primera.getCantidadentrada());
                            j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
                        }
                        if (primera.getEntrada().equals("acero") && primera.getCantidadentrada() <= j.getAcero()) {
                            j.setAcero(j.getAcero() - primera.getCantidadentrada());
                            j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
                        }
                        if (primera.getEntrada().equals("objeto") && primera.getCantidadentrada() <= j.getObjeto()) {
                            j.setObjeto(j.getObjeto() - primera.getCantidadentrada());
                            j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
                        }
                        e.setPosicion(12);
                        e.setMazo(null);
                    }
                }
            }
        }

        final List<Integer> GRAN_DRAGONES = Arrays.asList(13, 48);
        if (GRAN_DRAGONES.stream()
                .anyMatch(cartaId -> tabla.estaEnTablero(cartaId) && !tabla.tieneEnanoEncima(cartaId))) {
            // Si no se defiende le quita 1 de oro a cada jugador
            tabla.getJugadores().forEach(jugador -> {
                if (jugador.getOro() > 0)
                    jugador.setOro(jugador.getOro() - 1);
            });
        }

        final List<Integer> DRAGONES = Arrays.asList(22, 27, 35);
        if (DRAGONES.stream().anyMatch(cartaId -> tabla.estaEnTablero(cartaId) && !tabla.tieneEnanoEncima(cartaId))) {
            // Si no se defiende le quita 1 de oro a cada jugador
            tabla.getJugadores().forEach(jugador -> {
                if (jugador.getOro() > 0)
                    jugador.setOro(jugador.getOro() - 1);
            });
        }
        final List<Integer> KNOCKERS = Arrays.asList(14, 42, 43, 53);
        if (KNOCKERS.stream().anyMatch(cartaId -> tabla.estaEnTablero(cartaId) && !tabla.tieneEnanoEncima(cartaId))) {
            // Si no se defiende le quita 1 de hierro a cada jugador
            tabla.getJugadores().forEach(jugador -> {
                if (jugador.getHierro() > 0)
                    jugador.setHierro(jugador.getHierro() - 1);
            });
        }

        final List<Integer> SIDHES = Arrays.asList(37, 38, 47);
        if (SIDHES.stream().anyMatch(cartaId -> tabla.estaEnTablero(cartaId) && !tabla.tieneEnanoEncima(cartaId))) {
            // Si no se defiende cambia 2 oro por 2 hierro
            tabla.getJugadores().forEach(jugador -> {
                if (jugador.getOro() >= 2) {
                    jugador.setOro(jugador.getOro() - 2);
                    jugador.setHierro(jugador.getHierro() + 2);
                }
            });
        }

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

        return "redirect:/partida/" + id;
    }

    @Transactional
    @GetMapping("{partidaId}/borrar_partida")
    public String borrarPartida(@PathVariable("partidaId") Integer id) {
        // Acaba cuando un jugador tiene 4 objetos o la baraja no tiene mas cartas

        return "redirect:/";
    }
}
