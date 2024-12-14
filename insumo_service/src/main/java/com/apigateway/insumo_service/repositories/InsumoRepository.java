package com.apigateway.insumo_service.repositories;

import com.apigateway.insumo_service.models.entities.Insumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsumoRepository extends JpaRepository<Insumo, Long> {
    boolean existsByCodigo(String codigo); // Método para verificar si el código ya existe

    Insumo findById(long id);


    Insumo findTopByOrderByIdDesc(); // Método para obtener el último insumo insertado

    @Query("SELECT i FROM Insumo i WHERE " +
            "i.categoriaInsumoId ILIKE CONCAT('%', :filter, '%') OR " +
            "i.proveedorId ILIKE CONCAT('%', :filter, '%') OR " +
            "i.marcaInsumoId ILIKE CONCAT('%', :filter, '%') OR " +
            "i.codigo ILIKE CONCAT('%', :filter, '%') OR " +
            "i.nombre ILIKE CONCAT('%', :filter, '%') OR " +
            "i.descripcion ILIKE CONCAT('%', :filter, '%') OR " +
            "i.color ILIKE CONCAT('%', :filter, '%')")
    Page<Insumo> findByFilter(String filter, Pageable pageable);

    @Query("SELECT i FROM Insumo i WHERE " +
            "i.codigo ILIKE CONCAT('%', :filter, '%') OR " +
            "i.nombre ILIKE CONCAT('%', :filter, '%') OR " +
            "i.color ILIKE CONCAT('%', :filter, '%') " +
            "ORDER BY i.id DESC")
    List<Insumo> findByGlobalFilter(@Param("filter") String filter);
}
