package dev.hotel.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;


@Service
public class ClientService {

	private ClientRepository clientRepository;
	
	
	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}


	public List<Client> listerClient(Integer numeroPage, Integer taille) {
		return clientRepository.findAll(PageRequest.of(numeroPage, taille)).getContent();
	}
	
	public Optional<Client> recupererClient(UUID uuid) {
		return clientRepository.findById(uuid);
	}
	
	@Transactional
    public Client creerNouveauClient(String nom, String prenoms) {
        Client nouveauClient = new Client(nom, prenoms);
        return clientRepository.save(nouveauClient);
    }

	
}
