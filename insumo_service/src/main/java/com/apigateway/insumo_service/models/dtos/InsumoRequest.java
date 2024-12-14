package com.apigateway.insumo_service.models.dtos;

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
public class InsumoRequest {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "El campo nombre solo debe contener caracteres alfanuméricos.")
    private String categoriaInsumoId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "El campo nombre solo debe contener caracteres alfanuméricos.")
    private String proveedorId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "El campo nombre solo debe contener caracteres alfanuméricos.")
    private String marcaInsumoId;

    @Size(min = 11, max = 11, message = "El campo codigo debe tener entre 11 dígitos.")
    private String codigo;

    @NotNull(message = "El campo nombre es obligatorio.")
    @Size(min = 1, max = 100, message = "PRUEBA.")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚ\\s]*$", message = "El campo nombre solo debe contener caracteres alfanuméricos.")
    private String nombre;

    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9_\\-\\s,\\.]*$", message = "El campo descripcion solo debe contener letras, números y algunos caracteres especiales.")
    private String descripcion;

    @NotNull(message = "El campo color es obligatorio.")
    @Size(min = 1, max = 50, message = "El campo color debe tener entre 1 y 50 caracteres.")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "El campo color solo debe contener caracteres alfabéticos.")
    private String color;


    private Integer ingreso;


    private Integer salida;


    private Integer saldo;


    private Boolean estado;

}
