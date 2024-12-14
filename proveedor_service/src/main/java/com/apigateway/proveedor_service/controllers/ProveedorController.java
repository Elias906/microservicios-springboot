package com.apigateway.proveedor_service.controllers;

import com.apigateway.proveedor_service.models.dtos.ProveedorRequest;
import com.apigateway.proveedor_service.models.dtos.ProveedorResponse;
import com.apigateway.proveedor_service.models.entities.Proveedor;
import com.apigateway.proveedor_service.services.ProveedorService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping("/addProveedor")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addProveedor(@Valid @RequestBody ProveedorRequest proveedorRequest) {
        String mensaje = proveedorService.addProveedor(proveedorRequest);

        if (mensaje != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("0", mensaje));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("1", "Proveedor creado con éxito."));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProveedorResponse> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "filter", defaultValue = "") String filter
    ){
        return this.proveedorService.getAllProveedores(page, size, filter);
    }

    //Obtener un proveedor
    @GetMapping("/getProveedor/{idProveedor}")
    @ResponseStatus(HttpStatus.OK)
    public ProveedorResponse getProveedorById(@PathVariable String idProveedor) {
        return proveedorService.getProveedorById(idProveedor);
    }

    //Actualizar un proveedor
    @PutMapping("/updateProveedor/{idProveedor}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateProveedor(
            @PathVariable String idProveedor,
            @Valid @RequestBody ProveedorRequest proveedorRequest) {
        ProveedorResponse proveedorResponse = proveedorService.updateProveedor(idProveedor, proveedorRequest);

        if (proveedorResponse != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("1", "Proveedor actualizado con éxito."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("0", "Error al actualizar el proveedor."));
        }
    }

    //Eliminar proveedor
    @DeleteMapping("/deleteProveedor/{idProveedor}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteProveedor(@PathVariable String idProveedor) {
        boolean isDeleted = proveedorService.deleteProveedor(idProveedor);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("1", "Proveedor eliminado con éxito."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("0", "Error al eliminar el proveedor."));
        }
    }

    //Select proveedores
    @GetMapping("/selectProveedoresServ")
    public ResponseEntity<?> getClientes(@RequestParam(value = "global_filter", required = false) String globalFilter) {
        try {
            List<Proveedor> clientes = proveedorService.getProveedores(globalFilter);

            if (clientes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("0", "No se encontraron proveedores con esos filtros."));
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
