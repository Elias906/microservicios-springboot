package com.apigateway.cliente_service.controllers;

import com.apigateway.cliente_service.model.dtos.ClienteRequest;
import com.apigateway.cliente_service.model.dtos.ClienteResponse;
import com.apigateway.cliente_service.model.entities.Cliente;
import com.apigateway.cliente_service.services.ClienteService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/addCliente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addCliente(@Valid @RequestBody ClienteRequest clienteRequest) {
        String mensaje = clienteService.addCliente(clienteRequest);

        if (mensaje != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("0", mensaje));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("1", "Cliente creado con éxito."));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ClienteResponse> getAllClients(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "filter", defaultValue = "") String filter
    ){
        return clienteService.getAllClientes(page, size, filter);
    }

    //Obtener un cliente
    @GetMapping("/getCliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponse getClienteById(@PathVariable long id) {
        return clienteService.getClienteById(id);
    }

    //Actualizar un cliente
    @PutMapping("/updateCliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateCliente(
            @PathVariable long id,
            @Valid @RequestBody ClienteRequest clienteRequest) {
        ClienteResponse clienteResponse = clienteService.updateCliente(id, clienteRequest);

        if (clienteResponse != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("1", "Cliente actualizado con éxito."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("0", "Error al actualizar el cliente."));
        }
    }

    //Eliminar cliente
    @DeleteMapping("/deleteCliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteCliente(@PathVariable long id) {
        boolean isDeleted = clienteService.deleteCliente(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("1", "Cliente eliminado con éxito."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("0", "Error al eliminar el cliente."));
        }
    }

    //Select clientes
    @GetMapping("/selectClientesServ")
    public ResponseEntity<?> getClientes(@RequestParam(value = "global_filter", required = false) String globalFilter) {
        try {
            List<Cliente> clientes = clienteService.getClientes(globalFilter);

            if (clientes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("0", "No se encontraron clientes con esos filtros."));
            }

            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("0", "Ha ocurrido un error interno."));
        }
    }

    // Clases para manejar respuestas de éxito y error
    public static class ErrorResponse {
        @JsonProperty("exito")
        private String exito;

        @JsonProperty("message")
        private String message;

        public ErrorResponse(String exito, String message) {
            this.exito = exito;
            this.message = message;
        }


    }

    public static class SuccessResponse {
        @JsonProperty("exito")
        private String exito;
        @JsonProperty("message")
        private String message;

        public SuccessResponse(String exito, String message) {
            this.exito = exito;
            this.message = message;
        }


    }

}
