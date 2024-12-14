package com.apigateway.cliente_service.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_cliente", nullable = true, length = 150)
    private String tipoCliente;

    @Column(name = "tipo_documento", nullable = true, length = 150)
    private String tipoDocumento;

    @Column(name = "distrito", nullable = true, length = 150)
    private String distrito;

    @Column(name = "nombres", nullable = false, length = 150)
    private String nombres;

    @Column(name = "apellido_paterno", nullable = false, length = 150)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = true, length = 150)
    private String apellidoMaterno;

    @Column(name = "nro_documento", nullable = true, length = 50)
    private String nroDocumento;

    @Column(name = "direccion", nullable = true, length = 255)
    private String direccion;

    @Column(name = "celular", nullable = true, length = 9)
    private String celular;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_editado", nullable = false)
    private LocalDateTime fechaEditado;

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", tipoClienteId=" + tipoCliente +
                ", tipoDocumentoId=" + tipoDocumento +
                ", distritoId=" + distrito +
                ", nombres='" + nombres + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", nroDocumento='" + nroDocumento + '\'' +
                ", direccion='" + direccion + '\'' +
                ", celular='" + celular + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", fechaEditado=" + fechaEditado +
                '}';
    }
}
