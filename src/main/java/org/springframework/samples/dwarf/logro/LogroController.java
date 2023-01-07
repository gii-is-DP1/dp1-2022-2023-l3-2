package org.springframework.samples.dwarf.logro;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LogroController {

    private LogroService logroService;

    private String VIEW_LOGROS = "logros/showAllLogros";

    @Autowired
    public LogroController(LogroService logroService) {
        this.logroService = logroService;
    }

    @GetMapping("/logro/")
    public String showAll(Map<String, Object> model) {
        List<Logro> logros = logroService.findAll();
        model.put("logros", logros);

        return VIEW_LOGROS;
    }

    @Transactional
    @GetMapping("/logros/mod")
    public String updateLogro(Map<String, Object> model) {

        model.put("logro", new Logro());
        return "logros/modif";
    }

    @ModelAttribute("tipos")
    public Collection<TipoLogro> tiposLogros() {
        return this.logroService.tiposDeLogros();
    }

    @GetMapping("/logros/create")
    public String createLogro(Map<String, Object> model) {

        model.put("logro", new Logro());

        return "logros/create";
    }

    @Transactional()
    @PostMapping("/logros/create")
    public String createLogro(@Valid Logro logro, BindingResult result, RedirectAttributes redatt) {
        if (result.hasErrors()) {
            redatt.addFlashAttribute("error", result.getAllErrors());
            return "redirect:/logros/create";
        } else {

            logroService.save(logro);

            return "redirect:/logro/";

        }

    }

    @Transactional()
    @PostMapping("/logros/mod")
    public String updateLogro(@Valid Logro logro, BindingResult result, RedirectAttributes redatt) {
        if (result.hasErrors()) {
            redatt.addFlashAttribute("error", result.hasErrors());
            return "logros/modif";
        } else {

            logroService.save(logro);

            return "redirect:/logro/";

        }

    }


    @Transactional
    @GetMapping("/logros/del")
    public String delLogro(Map<String, Object> model, @RequestParam("logro") Integer id) {
        logroService.delLogro(id);
        return "redirect:/logro/";
    }
}
