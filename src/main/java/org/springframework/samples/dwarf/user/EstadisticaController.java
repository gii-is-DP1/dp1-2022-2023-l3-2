package org.springframework.samples.dwarf.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.tablero.Tablero;
import org.springframework.samples.dwarf.tablero.TableroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estadistica")
public class EstadisticaController {

    private String GLOBAL_VIEW = "estadistica/showEstadisticaGlobal";

    private final EstadisticaService estadisticaService;
    private final TableroService tableroService;
    private final UserService userService;

    @Autowired
    public EstadisticaController(EstadisticaService estadisticaService, TableroService tableroService,
            UserService userService) {
        this.estadisticaService = estadisticaService;
        this.tableroService = tableroService;
        this.userService = userService;
    }

    @GetMapping("")
    public String showEstadistica(Map<String, Object> model) {

        List<Tablero> partidas = tableroService.findAllFinished();

        User authenticatedUser = userService.findAuthenticatedUser();

        final int GAMES_SHOWED = 10;

        model.put("partidas", tableroService.findLastNGames(GAMES_SHOWED));
        model.put("duracionMaxima", estadisticaService.getGamesMaxDuration(partidas));
        model.put("duracionMinima", estadisticaService.getGamesMinDuration(partidas));
        model.put("duracionMedia", estadisticaService.getGamesAverageDuration(partidas));
        model.put("global", true);
        model.put("authenticatedUser", authenticatedUser);

        return GLOBAL_VIEW;
    }

    @GetMapping("/{username}")
    public String showEstadisticaByUser(@PathVariable("username") String username, Map<String, Object> model) {

        User user = userService.findUser(username).get();
        List<Tablero> partidas = tableroService.findByUser(user).stream().filter(tab -> tab.isTerminada()).toList();

        User authenticatedUser = userService.findAuthenticatedUser();

        final int GAMES_SHOWED = 10;

        model.put("partidas", tableroService.findLastNGamesByUser(user, GAMES_SHOWED));
        model.put("duracionMaxima", estadisticaService.getGamesMaxDuration(partidas));
        model.put("duracionMinima", estadisticaService.getGamesMinDuration(partidas));
        model.put("duracionMedia", estadisticaService.getGamesAverageDuration(partidas));
        model.put("global", false);
        model.put("authenticatedUser", authenticatedUser);

        return GLOBAL_VIEW;
    }

}
