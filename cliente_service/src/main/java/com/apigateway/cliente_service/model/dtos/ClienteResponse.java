package com.apigateway.cliente_service.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponse {

    private Long id;
    private String tipoCliente;
    private String tipoDocumento;
    private String distrito;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nroDocumento;
    private String direccion;
    private String celular;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaEditado;
}
