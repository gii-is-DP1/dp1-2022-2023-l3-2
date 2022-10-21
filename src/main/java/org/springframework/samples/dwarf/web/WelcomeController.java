package org.springframework.samples.dwarf.web;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.dwarf.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping({ "/", "/welcome" })
    public String welcome(Map<String, Object> model) {

        List<Person> persons = new ArrayList<>();
        Person person = new Person();
        person.setFirstName("Alejandro");
        person.setLastName("García Sánchez-Hermosilla");
        persons.add(person);

        person = new Person();
        person.setFirstName("Juan Carlos");
        person.setLastName("León Madroñal");
        persons.add(person);

        person = new Person();
        person.setFirstName("Daniel");
        person.setLastName("Diáñez Suárez");
        persons.add(person);

        person = new Person();
        person.setFirstName("María");
        person.setLastName("Escalante Ramos");
        persons.add(person);

        person = new Person();
        person.setFirstName("Rafael David");
        person.setLastName("García Galocha");
        persons.add(person);

        model.put("persons", persons);
        model.put("group", "DP1-2022-2023-l3-2");
        model.put("title", "DWARF");

        return "welcome";
    }
}