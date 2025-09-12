package com.crudapi.crud.service;

import com.crudapi.crud.dto.client.ClientResponseDTO;
import com.crudapi.crud.dto.client.CreateClientDTO;
import com.crudapi.crud.dto.client.UpdateClientDTO;
import com.crudapi.crud.mapper.entityMapper.ClientMapper;
import com.crudapi.crud.model.Client;
import com.crudapi.crud.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public ClientResponseDTO createClient(CreateClientDTO dto) {
        log.info("Создание клиента: email={}, phone={}", dto.getEmail(), dto.getPhone());
        log.debug("Детали входного DTO: {}", dto);

        if (clientRepository.existsByEmail(dto.getEmail())) {
            log.error("Попытка создать клиента с существующим email: {}", dto.getEmail());
            throw new IllegalArgumentException("Client with email " + dto.getEmail() + " already exist");
        } else if (clientRepository.existsByPhone(dto.getPhone())) {
            log.error("Попытка создать клиента с существующим phone: {}", dto.getPhone());
            throw new IllegalArgumentException("Client with phone " + dto.getPhone() + " already exist");
        }

        Client client = clientMapper.mapToEntity(dto);
        log.debug("Преобразованный entity: {}", client);

        Client savedClient = clientRepository.save(client);
        log.info("Клиент сохранён с id={}", savedClient.getId());

        return clientMapper.mapToDTO(savedClient);
    }

    public ClientResponseDTO updateClient(UpdateClientDTO dto, Long id) {
        log.info("Обновление клиента с id={}", id);
        log.debug("Детали UpdateClientDTO: {}", dto);

        Client client = findClient(dto, id);
        log.debug("Найден клиент для обновления: {}", client);

        validateEmail(dto.getEmail(), client);

        Client updatedClient = clientRepository.save(client);
        log.info("Клиент обновлён: id={}", updatedClient.getId());

        return clientMapper.mapToDTO(updatedClient);
    }

    public void deleteClient(Long id) {
        log.info("Удаление клиента с id={}", id);

        if (!clientRepository.existsById(id)) {
            log.error("Попытка удалить несуществующего клиента с id={}", id);
            throw new IllegalArgumentException("Client with id " + id + " does not exist");
        }

        clientRepository.deleteById(id);
        log.info("Клиент с id={} успешно удалён", id);
    }

    public ClientResponseDTO findClientById(Long id) {
        log.info("Поиск клиента по id={}", id);

        return clientRepository.findById(id)
                .map(client -> {
                    log.debug("Клиент найден: {}", client);
                    return clientMapper.mapToDTO(client);
                })
                .orElseThrow(() -> {
                    log.error("Клиент с id={} не найден", id);
                    return new IllegalArgumentException("Client with id " + id + " does not exist");
                });
    }

    private Client findClient(UpdateClientDTO dto, Long id) {
        log.debug("Поиск клиента: id={}, email={}, phone={}", id, dto.getEmail(), dto.getPhone());

        if (id != null) {
            return clientRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Клиент с id={} не найден", id);
                        return new IllegalArgumentException("Client with id " + id + " does not exist");
                    });
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            return clientRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> {
                        log.error("Клиент с email={} не найден", dto.getEmail());
                        return new IllegalArgumentException("Client with email " + dto.getEmail() + " does not exist");
                    });
        }
        if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
            return clientRepository.findByPhone(dto.getPhone())
                    .orElseThrow(() -> {
                        log.error("Клиент с phone={} не найден", dto.getPhone());
                        return new IllegalArgumentException("Client with phone " + dto.getPhone() + " does not exist");
                    });
        }

        log.error("Ошибка: id, email или phone не переданы для поиска клиента");
        throw new IllegalArgumentException("id or email or phone is required");
    }

    private void validateEmail(String newEmail, Client client) {
        if (newEmail != null && !newEmail.isBlank() && !newEmail.equals(client.getEmail())) {
            log.info("Проверка уникальности нового email: {}", newEmail);

            if (clientRepository.existsByEmail(newEmail)) {
                log.error("Email {} уже используется другим клиентом", newEmail);
                throw new IllegalArgumentException("Client with email " + newEmail + " already exist");
            }
        }
    }
}
