package com.crudapi.crud.controller;

import com.crudapi.crud.dto.client.ClientResponseDTO;
import com.crudapi.crud.dto.client.CreateClientDTO;
import com.crudapi.crud.dto.client.UpdateClientDTO;
import com.crudapi.crud.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/client")
    @Operation(summary = "Create a new client")
    public ClientResponseDTO createClient (@RequestBody CreateClientDTO dto) {
        return clientService.createClient(dto);
    }

    @PutMapping("/client/{id}")
    @Operation(summary = "Update an existing client")
    public ClientResponseDTO updateClient (@PathVariable Long id, @RequestBody UpdateClientDTO dto) {
        return clientService.updateClient(dto, id);
    }

    @DeleteMapping("/client/{id}")
    @Operation(summary = "Delete an existing client")
    public boolean deleteClient (@PathVariable Long id) {
        clientService.deleteClient(id);
        return true;
    }

    @GetMapping("/client/{id}")
    @Operation(summary = "Get a client by id")
    public ClientResponseDTO getClientById(@PathVariable Long id) {
        return clientService.findClientById(id);
    }
}
