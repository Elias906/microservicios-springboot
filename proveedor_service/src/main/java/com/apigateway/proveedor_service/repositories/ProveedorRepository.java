package com.apigateway.proveedor_service.repositories;

import com.apigateway.proveedor_service.models.entities.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends MongoRepository<Proveedor, String> {
    boolean existsByRuc(String ruc);

    // Método para obtener un proveedor por idProveedor
    Proveedor findByIdProveedor(String idProveedor);

    // Método para buscar por columna (filtro global)
    @Query("{ '$or': [ " +
            "{ 'nombreProveedor': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'ruc': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'direccion': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'celular': { '$regex': ?0, '$options': 'i' } } " +
            "] }")
    Page<Proveedor> findByFilter(String filter, Pageable pageable);

    @Query("{ '$or': [ " +
            "{ 'nombreProveedor': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'ruc': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'estadoProveedor': { '$regex': ?0, '$options': 'i' } } " +
            "] }")
    List<Proveedor> findByGlobalFilter(@Param("filter") String filter);
}
