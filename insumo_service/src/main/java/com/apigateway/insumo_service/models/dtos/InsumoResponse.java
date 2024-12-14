package com.apigateway.insumo_service.models.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsumoResponse {

    private Long id;

    private String categoriaInsumoId;

    private String proveedorId;

    private String marcaInsumoId;

    private String codigo;
    private String nombre;

    private String descripcion;
    private String color;

    private Integer ingreso;
    private Integer salida;

    private Integer saldo;
    private Boolean estado;

    //Proveedor response
    private ProveedorResponse proveedor;


}
