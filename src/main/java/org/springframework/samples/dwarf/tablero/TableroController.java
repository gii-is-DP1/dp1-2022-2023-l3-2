package org.springframework.samples.dwarf.tablero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
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
            taservice.saveTablero(tabla);

            return "redirect:/partida/" + tabla.getId() + "/comienza";
        }
    }

    @Transactional
    @GetMapping("/{partidaId}")
    public String showTablero(@PathVariable("partidaId") Integer id, Model model, HttpServletResponse response) {
        /* response.addHeader("Refresh", "2"); */
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

        for (int i = 0; i < table.getJugadores().size(); i++) {
            Jugador j = table.getJugadores().get(i);
            Integer enanosSituados = j.getEnano().stream().filter(e -> e.getPosicion() != 12).toList().size();
            if (1 == enanosSituados) {
                j.setPrimerjugador(false);
                if (i == table.getJugadores().size() - 1) {
                    table.getJugadores().get(0).setPrimerjugador(true);
                    break;
                }
                table.getJugadores().get(i + 1).setPrimerjugador(true);
            }
        }
        for (int t = 0; t < table.getJugadores().size(); t++) {
            Jugador j = table.getJugadores().get(t);
            Integer enanosSituados = j.getEnano().stream().filter(e -> e.getPosicion() != 12).toList().size();
            if (2 == enanosSituados) {
                j.setPrimerjugador(false);
                if (t == table.getJugadores().size() - 1) {
                    table.getJugadores().get(0).setPrimerjugador(true);
                    break;
                }
                table.getJugadores().get(t + 1).setPrimerjugador(true);
            }
        }
        String username = table.getJugadores().stream().filter(j -> j.isPrimerjugador()).toList().get(0).getUser()
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

        return tablero1;
    }

    @Transactional
    @GetMapping("/{partidaId}/comienza")
    public String rondaPrincipio(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);
        for (int i = 0; i < 2; i++) {
            List<Carta> baraja = tabla.getMazos().get(tabla.getMazos().size() - 1).getCartas();
            Double num1 = Math.floor(Math.random() * baraja.size() + 1);
            final Integer numero1 = num1.intValue();
            Integer posicion1 = taservice.findCartaById(numero1).getPosicion();
            Mazo mazo = tabla.getMazos().get(posicion1 - 1);
            mazo.getCartas().add(0, taservice.findCartaById(numero1));
            Mazo mazo1 = tabla.getMazos().get(12);
            mazo1.getCartas().remove(taservice.findCartaById(numero1));
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

        return "redirect:/partida/" + id;
    }

    @Transactional
    @GetMapping("/{partidaId}/coloca")
    public String rondaColoca(@PathVariable("partidaId") Integer id, Model model, @RequestParam String username,
            @RequestParam Integer posicion) {

        Tablero tabla = taservice.findById(id);

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
                break;

            }
        }

        for (Jugador j : tabla.getJugadores()) {
            Integer cont = 0;
            for (Enano e : j.getEnano()) {
                if (e.getPosicion() != 12) {
                    cont++;
                    System.out.println("Esto es N:" + cont);
                }
            }
            if (cont < 2) {
                return "redirect:/partida/" + id;
            }
        }

        return "redirect:/partida/" + id + "/recursos";

    }

    @Transactional
    @GetMapping("{partidaId}/recursos")
    public String rondaRecursos(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);
        /*
         * Carta primera = tabla.getJugadores().stream().filter(jugador ->
         * jugador.getEnano())
         */
        tabla.getJugadores().stream().filter(j -> j.isPrimerjugador()).toList().get(0).setPrimerjugador(false);
        tabla.getJugadores().get(0).setPrimerjugador(true);

        for (Jugador j : tabla.getJugadores()) {
            for (Enano e : j.getEnano()) {
                if (e.getPosicion() != 12) {
                    Carta primera = e.getMazo().getFirstCarta();
                    if (primera.getDevuelve().equals("hierro")) {
                        j.setHierro(j.getHierro() + primera.getCantidaddevuelve());
                    }
                    if (primera.getDevuelve().equals("oro")) {
                        j.setOro(j.getOro() + primera.getCantidaddevuelve());
                    }
                    if (primera.getDevuelve().equals("medalla")) {
                        j.setMedalla(j.getMedalla() + primera.getCantidaddevuelve());
                    }
                    if (primera.getDevuelve().equals("acero")) {
                        j.setAcero(j.getAcero() + primera.getCantidaddevuelve());
                    }
                    if (primera.getDevuelve().equals("objeto")) {
                        j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
                    }
                    e.setPosicion(12);
                    e.setMazo(null);
                }
            }
        }

        // Sumamos 1 ronda
        tabla.setRonda(tabla.getRonda() + 1);

        return "redirect:/partida/" + id + "/comienza";
    }

}
