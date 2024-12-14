package com.apigateway.proveedor_service.models.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "proveedores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Proveedor {

    @Id
    private String idProveedor;

    private String nombreProveedor;

    private String ruc;

    private String direccion;

    private String celular;

    private String estadoProveedor = "0";

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    private LocalDateTime fechaEditado = LocalDateTime.now();

    @Override
    public String toString() {
        return "Proveedor{" +
                "idProveedor='" + idProveedor + '\'' +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", ruc='" + ruc + '\'' +
                ", direccion='" + direccion + '\'' +
                ", celular='" + celular + '\'' +
                ", estadoProveedor='" + estadoProveedor + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", fechaEditado=" + fechaEditado +
                '}';
    }

}
