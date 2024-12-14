package com.apigateway.cliente_service.repositories;

import com.apigateway.cliente_service.model.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByNroDocumento(String nroDocumento);

    Cliente findById(long id);

    @Query("SELECT c FROM Cliente c WHERE " +
            "LOWER(c.tipoCliente) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.tipoDocumento) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.distrito) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.nombres) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.apellidoPaterno) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.apellidoMaterno) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.nroDocumento) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.direccion) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.celular) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Cliente> findByFilter(String filter, Pageable pageable);

    @Query("SELECT c FROM Cliente c WHERE " +
            "CAST(c.id AS STRING) LIKE CONCAT('%', :filter, '%') OR " +  // Convertir id numérico a cadena
            "LOWER(c.nroDocumento) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.nombres) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.apellidoPaterno) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
            "LOWER(c.apellidoMaterno) LIKE LOWER(CONCAT('%', :filter, '%'))")
    List<Cliente> findByGlobalFilter(@Param("filter") String filter);
}
