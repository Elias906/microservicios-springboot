package com.apigateway.insumo_service.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ms_insumos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "categoria_insumo_id", nullable = true)
    private String categoriaInsumoId; // Relación con la tabla categorias_insumos

    @Column(name = "proveedor_id", nullable = true)
    private String proveedorId; // Relación con la tabla proveedores

    @Column(name = "marca_insumo_id", nullable = true)
    private String marcaInsumoId; // Relación con la tabla marcas_insumos

    @Column(name = "codigo", nullable = false, length = 11)
    private String codigo; // Código del insumo

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre; // Nombre del insumo

    @Column(name = "descripcion", nullable = true, columnDefinition = "TEXT")
    private String descripcion; // Descripción del insumo

    @Column(name = "color", nullable = false, length = 50)
    private String color; // Color del insumo

    @Column(name = "ingreso", nullable = true, columnDefinition = "INTEGER DEFAULT 0")
    private Integer ingreso; // Ingreso del insumo

    @Column(name = "salida", nullable = true, columnDefinition = "INTEGER DEFAULT 0")
    private Integer salida; // Salida del insumo

    @Column(name = "saldo", nullable = true, columnDefinition = "INTEGER DEFAULT 0")
    private Integer saldo; // Saldo del insumo

    @Column(name = "estado", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean estado; // Estado del insumo (activo/inactivo)

    @Override
    public String toString() {
        return "Insumo{" +
                "id=" + id +
                ", categoriaInsumoId='" + categoriaInsumoId + '\'' +
                ", proveedorId='" + proveedorId + '\'' +
                ", marcaInsumoId='" + marcaInsumoId + '\'' +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", color='" + color + '\'' +
                ", ingreso=" + ingreso +
                ", salida=" + salida +
                ", saldo=" + saldo +
                ", estado=" + estado +
                '}';
    }
}
