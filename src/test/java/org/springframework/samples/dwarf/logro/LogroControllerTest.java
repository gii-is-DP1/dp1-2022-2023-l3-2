package org.springframework.samples.dwarf.logro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dwarf.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.beans.HasProperty;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = LogroController.class, 
    excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class LogroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogroService logroService;
    
    @WithMockUser(value = "spring")
    @Test
    void testShowAll() throws Exception {
        mockMvc.perform(get("/logro/")).andExpect(status().isOk())
                .andExpect(view().name("logros/showAllLogros"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testUpdateLogro() throws Exception {
        mockMvc.perform(get("/logros/mod")).andExpect(status().isOk())
                .andExpect(model().attributeExists("logro"))
                .andExpect(view().name("logros/modif"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testCreateLogo() throws Exception {
        mockMvc.perform(get( "/logros/create")).andExpect(status().isOk())
                .andExpect(model().attributeExists("logro"))
                .andExpect(view().name("logros/create"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testCreateLogo1() throws Exception {
        mockMvc.perform(post("/logros/create").with(csrf())
                .param("name","nombre lobby")
                .param("description", "descripcion")
                .param("dificultad", "1")
                .param("requisito", "1")
                .param("tipo", "1"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(value = "spring")
    @Test
    void testUpdateLogro1() throws Exception {
        mockMvc.perform(post("/logros/mod").with(csrf())
                .param("name","nombre lobby")
                .param("description", "descripcion")
                .param("dificultad", "1")
                .param("requisito", "1")
                .param("tipo", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/logro/"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testDelLogro() throws Exception {
        mockMvc.perform(get("/logros/del"))
                .andExpect(status().isOk());
    }
    
}
