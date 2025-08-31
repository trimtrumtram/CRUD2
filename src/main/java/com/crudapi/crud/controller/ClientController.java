package com.crudapi.crud.controller;

import com.crudapi.crud.dto.client.ClientResponseDTO;
import com.crudapi.crud.dto.client.CreateClientDTO;
import com.crudapi.crud.dto.client.UpdateClientDTO;
import com.crudapi.crud.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Clients controller")
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/client")
    @Operation(
            summary = "Create a new client",
            description = "Creating a new client",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client created successfully"),
                    @ApiResponse(responseCode = "400", description = "Client is not created")
            }
    )
    public ClientResponseDTO createClient (@RequestBody CreateClientDTO dto) {
        return clientService.createClient(dto);
    }

    @PutMapping("/client/{id}")
    @Operation(
            summary = "Update an existing client",
            description = "Updating an existing client",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Client is not updated")
            }
    )
    public ClientResponseDTO updateClient (@PathVariable Long id, @RequestBody UpdateClientDTO dto) {
        return clientService.updateClient(dto, id);
    }

    @DeleteMapping("/client/{id}")
    @Operation(
            summary = "Delete an existing client",
            description = "Deleting an existing client",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Client is not deleted")
            }
    )
    public boolean deleteClient (@PathVariable Long id) {
        clientService.deleteClient(id);
        return true;
    }

    @GetMapping("/client/{id}")
    @Operation(
            summary = "Get a client by id",
            description = "Getting a client by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found successfully"),
                    @ApiResponse(responseCode = "400", description = "Client not found")
            }
    )
    public ClientResponseDTO getClientById(@PathVariable Long id) {
        return clientService.findClientById(id);
    }
}
