package dev.hotel.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import dev.hotel.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import dev.hotel.control.ClientController;
import dev.hotel.entite.Client;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClientService clientService;

	@Test
	void testListClient() throws Exception {

		Client client = new Client("bob", "l'éponge");
		Client client2 = new Client("rabby", "jacob");

		Mockito.when(clientService.listerClient(0, 2)).thenReturn(Arrays.asList(client, client2));

		mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=2")).andExpect(status().isOk())
				.andExpect(jsonPath("[0].nom").value("bob")).andExpect(jsonPath("[0].prenoms").value("l'éponge"))
				.andExpect(jsonPath("[1].nom").value("rabby")).andExpect(jsonPath("[1].prenoms").value("jacob"));

	}

	@Test
	void testGetClientUUID() throws Exception {
		Client client = new Client("bob", "l'éponge");
		UUID id = UUID.randomUUID();
		client.setUuid(id);

		Mockito.when(clientService.recupererClient(id)).thenReturn(Optional.of(client));

		mockMvc.perform(MockMvcRequestBuilders.get("/clients/{uuid}", id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.nom").value("bob")).andExpect(jsonPath("$.prenoms").value("l'éponge"));
	}

	@Test
	void testPostClientSuccess() throws Exception {

		Client client = new Client();
		client.setNom("nicoco");
		client.setPrenoms("lolilus");
		
		Mockito.when(clientService.creerNouveauClient("nicoco", "lolilus")).thenReturn(client);
		
		

		mockMvc.perform(MockMvcRequestBuilders.post("/clients").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nom\": \"nicoco\", \"prenoms\" : \"lolilus\"}").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
}
