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

        // Resultados finales
        if (table.getJugadores().get(0).getPosicionFinal() != null) {
            model.addAttribute("jugadoresOrdenados",
                    table.getJugadores().stream().sorted(Comparator.comparing(j -> j.getPosicionFinal())).toList());
        }

/*      Finalizacion automatica provisional 
        for(Jugador j: table.getJugadores()){
            if(j.getObjeto()==4){
                return "redirect:/partida/" + id + "/fin";
            }
        }
        if(table.getMazos().get(12).getCartas().size()==0){
            return "redirect:/partida/" + id + "/fin";
        } */
        return tablero1;
    }

    @Transactional
    @GetMapping("/{partidaId}/comienza")
    public String rondaPrincipio(@PathVariable("partidaId") Integer id) {
        Tablero tabla = taservice.findById(id);

            List<Carta> baraja = tabla.getMazos().get(tabla.getMazos().size() - 1).getCartas();
            Double num1 = Math.floor(Math.random() * baraja.size() + 1);
            Double num2 = Math.floor(Math.random() * baraja.size() + 1);
            final Integer numero1 = num1.intValue();
            final Integer numero2 = num2.intValue();
            Integer posicion1 = taservice.findCartaById(numero1).getPosicion();
            Integer posicion2 = taservice.findCartaById(numero2).getPosicion();
            if(posicion1 != posicion2){
                Mazo mazo = tabla.getMazos().get(posicion1 - 1);
                Mazo mazo2 = tabla.getMazos().get(posicion2 - 1);
                mazo.getCartas().add(0, taservice.findCartaById(numero1));
                mazo2.getCartas().add(0, taservice.findCartaById(numero2));
                Mazo mazo1 = tabla.getMazos().get(12); 
                mazo1.getCartas().remove(taservice.findCartaById(numero1));
                mazo1.getCartas().remove(taservice.findCartaById(numero2));
            }
            else if(posicion1==posicion2){
                Double num3 = Math.floor(Math.random() * baraja.size() + 1);
                final Integer numero3 = num3.intValue();
                Integer posicion3 = taservice.findCartaById(numero3).getPosicion();
                Mazo mazo = tabla.getMazos().get(posicion1 - 1);
                Mazo mazo2 = tabla.getMazos().get(posicion2 - 1);
                Mazo mazo3 = tabla.getMazos().get(posicion3 - 1);
                mazo.getCartas().add(0, taservice.findCartaById(numero1));
                mazo2.getCartas().add(0, taservice.findCartaById(numero2));
                mazo3.getCartas().add(0,taservice.findCartaById(numero3));
                Mazo mazo1 = tabla.getMazos().get(12); 
                mazo1.getCartas().remove(taservice.findCartaById(numero1));
                mazo1.getCartas().remove(taservice.findCartaById(numero2));
                mazo1.getCartas().remove(taservice.findCartaById(numero3));
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
