package dev.hotel.control;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.hotel.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import dev.hotel.dto.CreerClientRequestDto;
import dev.hotel.dto.CreerClientResponseDto;
import dev.hotel.entite.Client;

import javax.validation.Valid;

@RestController
@RequestMapping("clients")
public class ClientController {

	private ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	//@RequestMapping(method = RequestMethod.GET, path = "clients")
	@GetMapping
	public List<Client> listClient(@RequestParam int start, @RequestParam int size) {
		return clientService.listerClient(start, size);
	}

	//@RequestMapping(method = RequestMethod.GET, path = "clients/{uuid}")
	@GetMapping("{uuid}")
	public ResponseEntity<?> getClientUUID(@PathVariable UUID uuid) {

		Optional<Client> optClient = clientService.recupererClient(uuid);

		if (optClient.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optClient);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Veuillez entrer un autre identifiant client");
		}
	}
	
	
	
	@PostMapping
	public ResponseEntity<?> creerClient(@RequestBody @Valid CreerClientRequestDto client, BindingResult resultatValidation) {

		if (resultatValidation.hasErrors()) {
			return ResponseEntity.badRequest().body("Le prenom ou le nom ne comporte pas assez de caractère.");
		}

		return ResponseEntity.ok(new CreerClientResponseDto(clientService.creerNouveauClient(client.getNom(), client.getPrenoms())));
	}

}

