package org.springframework.samples.dwarf.tablero;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        this.jugadorService=jugadorService;
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
                if(i<=9) {
                    Carta carta = taservice.findCartaById(i);
                    List<Carta> cartas = new ArrayList<>();
                    Mazo mazo = new Mazo();
                    cartas.add(carta);
                    mazo.setPosicion(i);
                    mazo.setCartas(cartas);
                    mazos.add(mazo);
                } else if(i<13){
                    List<Carta> cartasEspeciales = taservice.findByPosicion(i);
                    Mazo mazo = new Mazo();
                    mazo.setCartas(cartasEspeciales);
                    mazo.setPosicion(i);
                    mazos.add(mazo);
                }else{
                    Mazo mazo = new Mazo();
                    List<Carta> cartas = new ArrayList<>();
                    
                    for(int j = 10 ; j <55;j++) {
                        Carta carta = new Carta();
                        carta = taservice.findCartaById(j);
                        cartas.add(carta);
                    }
                    mazo.setPosicion(i);
                    mazo.setCartas(cartas);
                    mazos.add(mazo);
                }
            }
            tabla.setMazos(mazos);
            tabla.setJugadores(jugadorService.findAll());
            taservice.saveTablero(tabla);

            return "redirect:/partida/" + tabla.getId() + "/comienza";
        }
    }

    @Transactional
    @GetMapping("/{partidaId}")
    public String showTablero(@PathVariable("partidaId") Integer id, Model model, HttpServletResponse response) {
      //  response.addHeader("Refresh", "2");
        Tablero table = taservice.findById(id);
        List<Mazo> mazo = table.getMazos();
        List<Mazo> mazo1 = mazo.subList(0, 3);
        List<Mazo> mazo2 = mazo.subList(3, 6);
        List<Mazo> mazo3 = mazo.subList(6, 9);
        List<Mazo> mazo4 = mazo.subList(9, 12);

        model.addAttribute("tablero1", mazo1);
        model.addAttribute("tablero2", mazo2);
        model.addAttribute("tablero3", mazo3);
        model.addAttribute("tablero4", mazo4);
        model.addAttribute("jugadores", table.getJugadores());
        return tablero1;
    }


    @Transactional
    @GetMapping("/{partidaId}/comienza")
    public String rondaPrincipio(@PathVariable("partidaId") Integer id, Model model) {
        for(int i = 0 ; i <2; i++) {
            Tablero tabla = taservice.findById(id);
            List<Carta> baraja = tabla.getMazos().get(tabla.getMazos().size()-1).getCartas();
            Double num1 = Math.floor(Math.random() * baraja.size() + 1);
            final Integer numero1= num1.intValue();
            Integer posicion1=taservice.findCartaById(numero1).getPosicion();
            Mazo mazo = tabla.getMazos().get(posicion1-1);
            mazo.getCartas().add(0, taservice.findCartaById(numero1));
            Mazo mazo1 = tabla.getMazos().get(12);
            mazo1.getCartas().remove(taservice.findCartaById(numero1));
        }
        return "redirect:/partida/" + id;
    }
}
