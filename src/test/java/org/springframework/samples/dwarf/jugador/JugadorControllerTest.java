package org.springframework.samples.dwarf.jugador;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dwarf.configuration.SecurityConfiguration;
import org.springframework.samples.dwarf.user.AuthoritiesService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = JugadorController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
public class JugadorControllerTest {
    
    private static final int TEST_JUGADOR_ID = 4;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JugadorService jugadorService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthoritiesService authoritiesService;

    private Jugador pepe;
    private User pepebiyuela;

	@BeforeEach
	void setup() {
		pepe = new Jugador();
		pepe.setId(TEST_JUGADOR_ID);
		pepe.setFirstName("Pepe");
		pepe.setLastName("Biyuela");
        pepe.setAcero(0);
        pepe.setEnanosDisponibles(null);
        pepe.setEsespectador(false);
        pepe.setHierro(0);
        pepe.setMedalla(0);
        pepe.setObjeto(0);
        pepe.setOro(0);
        pepe.setPosicionFinal(null);
        pepe.setPrimerjugador(false);
        pepe.setTurno(false);
        pepe.setUser(pepebiyuela);
		given(this.jugadorService.findOwnerById(4)).willReturn(pepe);
	}

    @WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/jugador/new")).andExpect(status().isOk()).andExpect(model().attributeExists("jugador"))
                .andExpect(view().name("jugadores/createOrUpdateOwnerForm"));
    }

    // @WithMockUser(value = "spring")
	// @Test
	// void testProcessCreationFormSuccess() throws Exception {
	// 	mockMvc.perform(post("/jugador/new")
    //             .param("firstName", "Daniel").with(csrf())
	// 			.param("lastName", "Diañez")
    //             .param("acero", "0")
    //             .param("enanosDisponibles", "null")
    //             .param("esespectador", "false")
    //             .param("hierro", "0")
    //             .param("medalla", "0")
    //             .param("objeto", "0")
    //             .param("oro", "0")
    //             .param("posicionFinal", "null")
    //             .param("primerjugador", "false")
    //             .param("turno", "false")
    //             .param("user", "dandiasua"))
	// 			.andExpect(status().is3xxRedirection());
	// }

    // @WithMockUser(value = "spring")
	// @Test
	// void testProcessCreationFormHasErrors() throws Exception {
	// 	mockMvc.perform(post("/jugadores/new").with(csrf()).param("firstName", "Pepe").param("lastName", "Biyuela")
	// 			.param("city", "London")).andExpect(status().isOk()).andExpect(model().attributeHasErrors("jugador"))
	// 			.andExpect(model().attributeHasFieldErrors("jugador", "address"))
	// 			.andExpect(model().attributeHasFieldErrors("jugador", "telephone"))
	// 			.andExpect(view().name("jugadores/createOrUpdateOwnerForm"));
	// }

    @WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/jugador/find")).andExpect(status().isOk())
                .andExpect(model().attributeExists("jugador"))
				.andExpect(view().name("jugadores/findOwners"));
	}
    
    // @WithMockUser(value = "spring")
	// @Test
	// void testProcessFindFormSuccess() throws Exception {
	// 	given(this.jugadorService.findOwnerByLastName("")).willReturn(Lists.newArrayList(pepe, new Jugador()));
	// 	mockMvc.perform(get("/jugadores")).andExpect(status().isOk()).andExpect(view().name("owners/ownersList"));
	// }

    @WithMockUser(value = "spring")
	@Test
	void testProcessFindFormByLastName() throws Exception {
		given(this.jugadorService.findOwnerByLastName(pepe.getLastName())).willReturn(Lists.newArrayList(pepe));

		mockMvc.perform(get("/jugador").param("lastName", "Biyuela")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/jugador/" + TEST_JUGADOR_ID));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoOwnersFound() throws Exception {
		mockMvc.perform(get("/jugador").param("lastName", "Unknown Surname")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("jugador", "lastName"))
				.andExpect(model().attributeHasFieldErrorCode("jugador", "lastName", "notFound"))
				.andExpect(view().name("jugadores/findOwners"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testInitUpdateOwnerForm() throws Exception {
		mockMvc.perform(get("/jugador/{ownerId}/edit", TEST_JUGADOR_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("jugador"))
				.andExpect(model().attribute("jugador", hasProperty("lastName", is("Biyuela"))))
				.andExpect(model().attribute("jugador", hasProperty("firstName", is("Pepe"))))
				.andExpect(model().attribute("jugador", hasProperty("acero",is(0))))
                .andExpect(model().attribute("jugador", hasProperty("hierro",is(0))))
                .andExpect(model().attribute("jugador", hasProperty("medalla",is(0))))
                .andExpect(model().attribute("jugador", hasProperty("objeto",is(0))))
                .andExpect(model().attribute("jugador", hasProperty("oro",is(0))))
                .andExpect(model().attribute("jugador", hasProperty("esespectador", is(false))))
                .andExpect(model().attribute("jugador", hasProperty("primerjugador", is(false))))
                .andExpect(model().attribute("jugador", hasProperty("turno", is(false))))
				.andExpect(view().name("jugadores/createOrUpdateOwnerForm"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		mockMvc.perform(post("/jugador/{ownerId}/edit", TEST_JUGADOR_ID).with(csrf())
                .param("firstName", "Jose")
				.param("lastName", "Villa")
				.param("acero", "50"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/jugador/{ownerId}"));
	}

    // @WithMockUser(value = "spring")
	// @Test
	// void testProcessUpdateOwnerFormHasErrors() throws Exception {
	// 	mockMvc.perform(post("/jugador/{ownerId}/edit", TEST_JUGADOR_ID).with(csrf()).param("firstName", "Jose")
	// 			.param("lastName", "Villa")).andExpect(status().isOk())
	// 			.andExpect(model().attributeHasErrors("jugador"))
	// 			.andExpect(model().attributeHasFieldErrors("jugador", "address"))
	// 			.andExpect(model().attributeHasFieldErrors("jugador", "telephone"))
	// 			.andExpect(view().name("jugadores/createOrUpdateOwnerForm"));
	// }

	@WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		mockMvc.perform(get("/jugador/{ownerId}", TEST_JUGADOR_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("jugador", hasProperty("lastName", is("Biyuela"))))
				.andExpect(model().attribute("jugador", hasProperty("firstName", is("Pepe"))))
				.andExpect(view().name("jugadores/ownerDetails"));
	}

}
