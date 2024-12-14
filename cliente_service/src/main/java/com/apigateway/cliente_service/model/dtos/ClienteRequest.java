package com.apigateway.cliente_service.model.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteRequest {

    @NotNull(message = "El campo tipo de cliente es obligatorio.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El campo tipoCliente solo debe contener letras.")
    private String tipoCliente;

    @NotNull(message = "El campo tipo de documento es obligatorio.")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "El campo tipoDocumento solo debe contener dígitos y letras mayúsculas.")
    private String tipoDocumento;

    @NotNull(message = "El campo distrito es obligatorio.")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚÜüñÑ\\s.,-]+$", message = "El campo distrito solo debe contener caracteres alfanuméricos, espacios, puntos, comas y guiones.")
    private String distrito;

    @NotNull(message = "El campo nombres es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El campo nombres solo debe contener letras.")
    @Size(min = 2, message = "El campo nombres debe tener al menos 2 caracteres.")
    private String nombres;

    @NotNull(message = "El campo apellido paterno es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El campo apellidoPaterno solo debe contener letras.")
    @Size(min = 2, message = "El campo apellidoPaterno debe tener al menos 2 caracteres.")
    private String apellidoPaterno;

    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*$", message = "El campo apellido materno solo debe contener letras.")
    @Size(min = 2, message = "El campo apellidoMaterno debe tener al menos 2 caracteres.")
    private String apellidoMaterno;

    @NotNull(message = "El campo nro. de documento es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9_\\-\\s,\\.]+$", message = "El campo nroDocumento solo debe contener letras, números y algunos caracteres especiales.")
    @Size(min = 8, max = 11, message = "El campo nroDocumento debe tener entre 8 y 11 caracteres.")
    private String nroDocumento;

    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9_\\-\\s,\\.]+$", message = "El campo direccion solo debe contener letras, números y algunos caracteres especiales.")
    @Size(min = 5, message = "El campo direccion debe tener al menos 5 caracteres.")
    private String direccion;

    @Pattern(regexp = "^[0-9]+$", message = "El campo celular solo debe contener números.")
    @Size(min = 9, max = 9, message = "El campo celular debe tener 9 dígitos.")
    private String celular;

    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaEditado;
}
