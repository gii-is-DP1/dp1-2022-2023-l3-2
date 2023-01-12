package org.springframework.samples.dwarf.tablero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.samples.dwarf.carta.Carta;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.user.InvitacionAmistadService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TableroService {

    private TableroRepository repo;
    private JugadorService jugadorService;
    private UserService userService;
    private InvitacionAmistadService invitacionAmistadService;

    @Autowired
    public TableroService(TableroRepository repo, JugadorService jugadorService, @Lazy UserService userService,
            InvitacionAmistadService invitacionAmistadService) {
        this.repo = repo;
        this.jugadorService = jugadorService;
        this.userService = userService;
        this.invitacionAmistadService = invitacionAmistadService;
    }

    @Transactional(readOnly = true)
    public List<Tablero> findAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Tablero> findAllFinished() {
        return repo.findAllFinished();
    }

    @Transactional(readOnly = true)
    public List<Tablero> findAllNotFinished() {
        return repo.findAllNotFinished();
    }

    @Transactional(readOnly = true)
    public Tablero findById(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public void saveTablero(Tablero tabla) {
        repo.save(tabla);
    }

    @Transactional(readOnly = true)
    public List<Carta> findAllCartas() {
        return repo.findAllCartas();
    }

    @Transactional(readOnly = true)
    public Carta findCartaById(Integer id) {
        return repo.findCartaById(id);
    }

    @Transactional(readOnly = true)
    public List<Carta> findByPosicion(Integer posicion) {
        return repo.findByPosicion(posicion);
    }

    @Transactional
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Tablero> findByUser(User user) {
        List<Tablero> all = findAll();
        return all.stream().filter(
                tab -> tab.getJugadores().stream().anyMatch(j -> j.getUser().getUsername().equals(user.getUsername())))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Tablero> findEnCursoByUser(User user) {
        return findByUser(user).stream().filter(tab -> !tab.isTerminada()).toList();
    }

    // NO HECHO CON QUERY
    @Transactional(readOnly = true)
    public List<Tablero> findLastNGamesByUser(User user, Integer n) {
        return repo.findAll().stream()
                .filter(t -> t.getJugadores().stream().anyMatch(j -> j.getUser().equals(user)))
                .filter(t -> t.isTerminada())
                .sorted(Comparator.comparing(Tablero::getFinishedAt).reversed())
                .limit(n)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Tablero> findLastNGames(Integer n) {
        return repo.findLastGames().stream().filter(tab -> tab.isTerminada()).limit(n).toList();
    }

    @Transactional
    public Tablero saveTableroFromProcess(String name, String username1, String username2, String username3) {
        Tablero tabla = new Tablero();
        tabla.setName(name);

        List<Mazo> mazos = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            if (i <= 9) {
                Carta carta = findCartaById(i);
                List<Carta> cartas = new ArrayList<>();
                Mazo mazo = new Mazo();
                cartas.add(carta);
                mazo.setPosicion(i);
                mazo.setCartas(cartas);
                mazos.add(mazo);
            } else if (i < 13) {
                List<Carta> cartasEspeciales = findByPosicion(i);
                Mazo mazo = new Mazo();
                mazo.setCartas(cartasEspeciales);
                mazo.setPosicion(i);
                mazos.add(mazo);
            } else {
                Mazo mazo = new Mazo();
                List<Carta> cartas = new ArrayList<>();

                for (int j = 10; j < 55; j++) {
                    Carta carta = new Carta();
                    carta = findCartaById(j);
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

        saveTablero(tabla);

        return tabla;
    }

    @Transactional(readOnly = true)
    public boolean puedoSerEspectador(Tablero table) {

        if (!table.getJugadores().stream().map(j -> j.getUser()).toList()
                .contains(userService.findAuthenticatedUser())) {
            if (!table.getJugadores().stream().map(j -> j.getUser()).toList()
                    .containsAll(invitacionAmistadService.findFriends(userService.findAuthenticatedUser()).stream()
                            .map(j -> j.getUserrecibe()).toList())
                    || invitacionAmistadService.findFriends(userService.findAuthenticatedUser()).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Transactional
    public void sacarYColocarCartasDeBaraja(Tablero tabla) {
        // Coger dos cartas aleatorias de la baraja y colocarlas
        List<Carta> baraja = tabla.getMazos().get(tabla.getMazos().size() - 1).getCartas();

        Carta cartaTemporal = null;
        for (int i = 0; i < 2; i++) {
            int DESDE = 0;
            int HASTA = baraja.size() - 1;

            int indexCarta = (int) (Math.random() * (HASTA - DESDE + 1) + DESDE);

            Carta carta = tabla.getMazos().get(12).getCartas().get(indexCarta);
            if (i == 0) {
                cartaTemporal = carta;
            }
            tabla.getMazos().stream().filter(mazo -> mazo.getPosicion() == carta.getPosicion()).toList().get(0)
                    .getCartas()
                    .add(0, carta);
            baraja.remove(carta);
            if (i == 1 && cartaTemporal.getPosicion().equals(carta.getPosicion())) {
                int indexCarta3 = (int) (Math.random() * (HASTA - DESDE + 1) + DESDE);
                Carta carta3 = tabla.getMazos().get(12).getCartas().get(indexCarta3);
                tabla.getMazos().stream().filter(mazo -> mazo.getPosicion() == carta3.getPosicion()).toList().get(0)
                        .getCartas()
                        .add(0, carta3);
                baraja.remove(carta3);
            }
            if (baraja.size() == 0)
                break;
        }
    }

    @Transactional
    public void descolocarEnanos(Tablero tabla) {
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
    }

    @Transactional
    public void colocarEnanos(Tablero tabla, String username, Integer posicion) {
        List<Enano> enanosJugador = tabla.getEnanosByUsername(username);
        Jugador jugadorActual = tabla.getJugadorActual(username);

        for (Enano e : enanosJugador) {
            if (e.getPosicion() == 12) {
                e.setPosicion(posicion);
                for (Mazo mazo : tabla.getMazos()) {
                    if (10 <= mazo.getPosicion() && 13 > mazo.getPosicion() && enanosJugador.size() >= 1
                            && tabla.getJugadores().stream()
                                    .filter(jugador -> jugador.getUser().getUsername().equals(username))
                                    .map(j -> j.getMedalla()).toList().get(0) >= 4
                            && mazo.getPosicion() == posicion) {
                        tabla.getJugadorByUsername(username)
                                .setMedalla(tabla.getJugadorByUsername(username).getMedalla() - 4);
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
    }

    @Transactional
    public void setSiguienteTurno(Tablero tabla, String username) {
        Jugador jugadorActual = tabla.getJugadorActual(username);

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
    }

    @Transactional
    public void quitarTurnoActual(Tablero tabla) {
        tabla.getJugadores().stream().filter(j -> j.isTurno()).toList().get(0).setTurno(false);
    }

    @Transactional
    public void ejecutarAccionesCartasEnRecursos(Tablero tabla) {
        boolean farmeo = true;
        int accion = 1;
        farmeo = tabla.analizarDefensas();
        while (accion <= 5) {
            for (Jugador j : tabla.getJugadores()) {
                for (Enano e : j.getEnano()) {
                    if (e.getPosicion() != 12) {
                        Carta primera = e.getMazo().getFirstCarta();
                        if (accion == 2)
                            primera.accion2(tabla, j, e);
                        if (accion == 3 && farmeo)
                            primera.accion3(tabla, j, e);
                        if (accion == 4)
                            primera.accion4(tabla, j, e);
                    }
                }
            }
            accion++;
        }
    }

    @Transactional
    public void setTurnoToPrimerJugador(Tablero tabla) {
        tabla.getJugadores().stream().filter(j -> j.isPrimerjugador()).toList().get(0).setTurno(true);
    }

    @Transactional
    public void incrementarRonda(Tablero tabla) {
        tabla.setRonda(tabla.getRonda() + 1);
    }

    @Transactional
    public void incrementarEnanosPorAyuda(Tablero tabla, String username) {
        Jugador jugadorActual = tabla.getJugadorActual(username);
        jugadorActual.setEnanosDisponibles(jugadorActual.getEnanosDisponibles() + 2);
    }

    @Transactional
    public String ejecutarAccionEnColoca(Tablero tabla, Integer posicion, String username) {
        Jugador jugadorActual = tabla.getJugadorActual(username);
        return tabla.getMazos().get(posicion - 1).getFirstCarta().accion5(tabla, jugadorActual);
    }

    @Transactional
    public void calcularPosicionesFinales(Tablero tabla) {
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

    }

    @Transactional
    public void setMaterialesDeEleccion(Tablero tabla, String username, String material) {
        Jugador j = tabla.getJugadorByUsername(username);

        if (material.equals("oro"))
            j.setOro(j.getOro() + 5);
        if (material.equals("hierro"))
            j.setHierro(j.getHierro() + 5);
        if (material.equals("acero"))
            j.setAcero(j.getAcero() + 5);
    }

    @Transactional
    public List<Carta> setCartasParaEleccion(Tablero tablero) {
        List<Carta> cartasTotal = findAllCartas();

        final int ULTIMA_CARTA_NORMAL = 9;
        final int CARTA_ESPECIAL_DE_ELECCION = 58;

        for (Mazo mazo : tablero.getMazos()) {
            if (mazo.getPosicion() > ULTIMA_CARTA_NORMAL) {
                if (mazo.getCartas().contains(findCartaById(CARTA_ESPECIAL_DE_ELECCION))) {
                    mazo.getCartas().remove(findCartaById(CARTA_ESPECIAL_DE_ELECCION));
                }
                cartasTotal.removeAll(mazo.getCartas());
            } else {

                cartasTotal.remove(mazo.getFirstCarta());
            }
        }
        return cartasTotal;
    }

    @Transactional
    public void moverCartaAlPrincipioDelMazo(Tablero tablero, Integer carta) {
        Carta cartaElegida = findCartaById(carta);

        Mazo mazoElegido = tablero.getMazos().stream().filter(m -> m.getPosicion().equals(cartaElegida.getPosicion()))
                .toList()
                .get(0);
        mazoElegido.getCartas().remove(cartaElegida);
        mazoElegido.getCartas().add(0, cartaElegida);
    }

    @Transactional(readOnly = true)
    public boolean isMyTurno(Tablero tablero) {
        return userService.findAuthenticatedUser().getUsername().equals(tablero.getUsernameByTurno());
    }

    @Transactional
    public void addMessage(Tablero tablero, User currentUser, String message) {

        ChatLine chatLine = new ChatLine();

        chatLine.setUsername(currentUser.getUsername());
        chatLine.setMensaje(message);

        tablero.getChat().add(chatLine);
    }
}
