package com.apigateway.proveedor_service.models.dtos;

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
public class ProveedorRequest {

    @NotNull(message = "El campo proveedor es obligatorio.")
    @Size(min = 1, max = 100, message = "El campo proveedor puede tener entre 1 y 100 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.-ñÑ]+$", message = "El campo proveedor solo debe contener caracteres alfanuméricos.")
    private String nombreProveedor;

    @NotNull(message = "El campo proveedor es obligatorio.")
    @Size(min = 11, max = 11, message = "El campo RUC dbe tener 11 dígitos.")
    @Pattern(regexp = "^[0-9]+$", message = "El campo RUC solo debe contener numeros sin espacios.")
    private String ruc;

    @NotNull(message = "El campo dirección es obligatorio.")
    @Size(min = 1, max = 100, message = "El campo dirección puede tener entre 1 y 100 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.-ñÑ]+$", message = "El campo dirección solo debe contener caracteres alfanuméricos.")
    private String direccion;

    @NotNull(message = "El campo celular es obligatorio.")
    @Size(min = 9, max = 9, message = "El campo celular debe contener 9 dígitos.")
    @Pattern(regexp = "^[0-9]+$", message = "El campo celular solo debe contener números.")
    private String celular;

    @Pattern(regexp = "^[01]$", message = "El estado del proveedor debe ser '0' o '1'.")
    private String estadoProveedor = "0";

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    private LocalDateTime fechaEditado = LocalDateTime.now();

}
