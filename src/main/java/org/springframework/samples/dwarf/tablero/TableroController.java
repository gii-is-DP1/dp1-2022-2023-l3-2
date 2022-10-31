package org.springframework.samples.dwarf.tablero;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public TableroController(TableroService service) {
        this.taservice=service;
    }




    @Transactional
    @GetMapping("/")
    public String showTablero(Map<String, Object> model){
        Tablero tabla = new Tablero();
		model.put("tablero", tabla);
        return tablero;
    }

    @Transactional
    @PostMapping("/")
    public String processTablero(@Valid Tablero tabla, BindingResult result) {
        if(result.hasErrors()) {
            return tablero;
        }
        else {
            List<Mazo> posicion = new ArrayList<>();
            for(int i =1 ; i<10 ; i++){
                Mazo res = new Mazo();
                res.setPosicion(String.format("/resources/images/Dimensionadas/000-ReversoCartasInicialesYMontaña.png"));
                posicion.add(res);
            }
            tabla.setMazos(posicion);
            taservice.saveTablero(tabla);
            return "redirect:/partida/" + String.format("%d", tabla.getId());
    }
    }
    @Transactional
    @GetMapping("/{partidaId}")
    public String showTablero(@PathVariable("partidaId") Integer id ,Model model){
        Tablero table = taservice.findById(id);
        List<Mazo> mazo = table.getMazos();
        List<Mazo> mazo1 = mazo.subList(0, 3);
        List<Mazo> mazo2 = mazo.subList(3, 6);
        List<Mazo> mazo3 = mazo.subList(6, 9);

        model.addAttribute("tablero1", mazo1);
        model.addAttribute("tablero2", mazo2);
        model.addAttribute("tablero3", mazo3);
        return tablero1;
    }

}
