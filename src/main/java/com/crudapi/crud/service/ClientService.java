package com.crudapi.crud.service;

import com.crudapi.crud.dto.client.ClientResponseDTO;
import com.crudapi.crud.dto.client.CreateClientDTO;
import com.crudapi.crud.dto.client.UpdateClientDTO;
import com.crudapi.crud.mapper.ClientMapper;
import com.crudapi.crud.model.Client;
import com.crudapi.crud.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public ClientResponseDTO createClient(CreateClientDTO dto) {
        if(clientRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException ("Client with email " + dto.getEmail() + " already exist");
        } else if (clientRepository.existsByPhone(dto.getPhone())) {
            throw new IllegalArgumentException ("Client with phone " + dto.getPhone() + " already exist");
        }

        Client client = clientMapper.mapToEntity(dto);
        return clientMapper.mapToDTO(clientRepository.save(client));
    }

    public ClientResponseDTO updateClient(UpdateClientDTO dto, Long id) {
        Client client = findClient(dto, id);
        validateEmail(dto.getEmail(), client);
        return clientMapper.mapToDTO(clientRepository.save(client));
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public ClientResponseDTO findClientById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::mapToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Client with id " + id + " does not exist"));
    }

    private Client findClient(UpdateClientDTO dto, Long id) {
        if(id != null) {
            return clientRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Client with id " + id + " does not exist"));
        }
        if(dto.getEmail() != null && !dto.getEmail().isBlank()) {
            return clientRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Client with email " + dto.getEmail() + " does not exist"));
        }
        if(dto.getPhone() != null && !dto.getPhone().isBlank()) {
            return clientRepository.findByPhone(dto.getPhone())
                    .orElseThrow(() -> new IllegalArgumentException("Client with phone " + dto.getPhone() + " does not exist"));
        }
        throw new IllegalArgumentException("id or email or phone is required");
    }

    private void validateEmail (String newEmail, Client client) {
        if(newEmail != null && !newEmail.isBlank() && !newEmail.equals(client.getEmail())) {
            if(clientRepository.existsByEmail(newEmail)) {
                throw new IllegalArgumentException ("Client with email " + newEmail + " already exist");
            }
        }
    }
}
