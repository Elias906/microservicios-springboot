package com.apigateway.insumo_service.controllers;

import com.apigateway.insumo_service.models.dtos.InsumoRequest;
import com.apigateway.insumo_service.models.dtos.InsumoResponse;
import com.apigateway.insumo_service.models.entities.Insumo;
import com.apigateway.insumo_service.services.InsumoService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService insumoService;

    @PostMapping("/addInsumo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addInsumo(@Valid @RequestBody InsumoRequest insumoRequest) {
        String mensaje = insumoService.addInsumo(insumoRequest);

        if (mensaje != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("0", mensaje));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("1", "Insumo creado con éxito."));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<InsumoResponse> getAllInsumos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "filter", defaultValue = "") String filter
    ){
        return insumoService.getAllInsumos(page, size, filter);
    }

    //Obtener un insumo
    @GetMapping("/getInsumo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InsumoResponse getInsumoById(@PathVariable long id) {
        return insumoService.getInsumoById(id);
    }

    //Actualizar un insumo
    @PutMapping("/updateInsumo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateInsumo(
            @PathVariable long id,
            @Valid @RequestBody InsumoRequest insumoRequest) {
        System.out.println("RESPUESTA PHP CONTROLADOR: "+insumoRequest.getIngreso());
        InsumoResponse insumoResponse = insumoService.updateInsumo(id, insumoRequest);

        if (insumoResponse != null) {
            System.out.println(insumoResponse);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("1", "Insumo actualizado con éxito."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("0", "Error al actualizar el insumo."));
        }
    }

    //Eliminar cliente
    @DeleteMapping("/deleteInsumo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteCliente(@PathVariable long id) {
        boolean isDeleted = insumoService.deleteInsumo(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("1", "Insumo eliminado con éxito."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("0", "Error al eliminar el insumo."));
        }
    }

    //Select clientes
    @GetMapping("/selectInsumosServ")
    public ResponseEntity<?> getInsumos(@RequestParam(value = "global_filter", required = false) String globalFilter) {
        try {
            List<Insumo> clientes = insumoService.getInsumos(globalFilter);

            if (clientes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("0", "No se encontraron insumos con esos filtros."));
            }

            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("0", "Ha ocurrido un error interno."));
        }
    }

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
