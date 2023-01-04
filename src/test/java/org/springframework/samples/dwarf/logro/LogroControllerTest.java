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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
        mockMvc.perform(get("/logros/")).andExpect(status().isOk())
                .andExpect(view().name("logros/showAllLogros"));
    }
    
}
