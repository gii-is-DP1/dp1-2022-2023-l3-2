package org.springframework.samples.dwarf.logro;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logros")
public class LogroController {

    private LogroService logroService;

    private String VIEW_LOGROS = "logros/showAllLogros";

    @Autowired
    public LogroController(LogroService logroService) {
        this.logroService = logroService;
    }

    @GetMapping("/")
    public String showAll(Map<String, Object> model) {
        List<Logro> logros = logroService.findAll();
        model.put("logros", logros);
        return VIEW_LOGROS;
    }
}
