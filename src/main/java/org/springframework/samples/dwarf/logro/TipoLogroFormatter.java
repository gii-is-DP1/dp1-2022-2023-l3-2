package org.springframework.samples.dwarf.logro;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class TipoLogroFormatter implements Formatter<TipoLogro> {
    @Autowired
    private LogroService logroService;

    @Override
    public String print(TipoLogro tipo, Locale arg1) {
        return tipo.getName();
    }

    @Override
    public TipoLogro parse(String nombre, Locale arg1) throws ParseException {
        for (TipoLogro l : logroService.tiposDeLogros()) {
            if (l.getName().equals(nombre)) {
                return l;
            }
        }
        throw new ParseException("Tipo no encontrado", 0);
    }

}
