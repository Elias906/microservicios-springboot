package com.apigateway.proveedor_service.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProveedorResponse {

    private String idProveedor;

    private String nombreProveedor;

    private String ruc;

    private String direccion;

    private String celular;

    private String estadoProveedor = "0";

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    private LocalDateTime fechaEditado = LocalDateTime.now();
}
