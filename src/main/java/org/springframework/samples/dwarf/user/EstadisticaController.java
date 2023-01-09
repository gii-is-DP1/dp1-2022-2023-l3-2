package org.springframework.samples.dwarf.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.tablero.Tablero;
import org.springframework.samples.dwarf.tablero.TableroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estadistica")
public class EstadisticaController {

    private String GLOBAL_VIEW = "estadistica/showEstadisticaGlobal";

    private final EstadisticaService estadisticaService;
    private final TableroService tableroService;

    @Autowired
    public EstadisticaController(EstadisticaService estadisticaService, TableroService tableroService) {
        this.estadisticaService = estadisticaService;
        this.tableroService = tableroService;
    }

    @GetMapping("")
    public String showEstadistica(Map<String, Object> model) {

        List<Tablero> partidas = tableroService.findAllFinished();

        final int GAMES_SHOWED = 10;

        model.put("partidas", tableroService.findLastNGames(GAMES_SHOWED));
        model.put("duracionMaxima", estadisticaService.getGamesMaxDuration(partidas));
        model.put("duracionMinima", estadisticaService.getGamesMinDuration(partidas));
        model.put("duracionMedia", estadisticaService.getGamesAverageDuration(partidas));

        return GLOBAL_VIEW;
    }

}
