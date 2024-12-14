package com.apigateway.proveedor_service.services;

import com.apigateway.proveedor_service.models.dtos.ProveedorRequest;
import com.apigateway.proveedor_service.models.dtos.ProveedorResponse;
import com.apigateway.proveedor_service.models.entities.Proveedor;
import com.apigateway.proveedor_service.repositories.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public String addProveedor(ProveedorRequest proveedorRequest){

        // Verificar si ya existe un cliente con el mismo nroDocumento
        if(proveedorRepository.existsByRuc(proveedorRequest.getRuc())){
            return "El proveedor con número de RUC " + proveedorRequest.getRuc() + " ya existe.";
        }

        var proveedor = Proveedor.builder()
                .nombreProveedor(proveedorRequest.getNombreProveedor())
                .ruc(proveedorRequest.getRuc())
                .direccion(proveedorRequest.getDireccion())
                .celular(proveedorRequest.getCelular())
                .estadoProveedor("0")
                .build();

        proveedorRepository.save(proveedor);

        log.info("Proveedor add: {}", proveedor);
        return null;
    }

    public Page<ProveedorResponse> getAllProveedores(int page, int size, String filter){

        var pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("idProveedor")));

        Page<Proveedor> proveedores;
        if (filter.isEmpty()) {
            proveedores = proveedorRepository.findAll(pageRequest);
        } else {
            proveedores = proveedorRepository.findByFilter(filter, pageRequest);
        }

        // Convertir a ProveedorResponse
        return proveedores.map(this::mapToProveedorResponse);
    }

    private ProveedorResponse mapToProveedorResponse(Proveedor proveedor){
        return ProveedorResponse.builder()
                .idProveedor(proveedor.getIdProveedor())
                .nombreProveedor(proveedor.getNombreProveedor())
                .ruc(proveedor.getRuc())
                .direccion(proveedor.getDireccion())
                .celular(proveedor.getCelular())
                .estadoProveedor(proveedor.getEstadoProveedor())
                .fechaRegistro(proveedor.getFechaRegistro())
                .fechaEditado(proveedor.getFechaEditado())
                .build();

    }

    //Obtener un proveedor
    public ProveedorResponse getProveedorById(String idProveedor) {
        Proveedor proveedor = proveedorRepository.findByIdProveedor(idProveedor);
        if (proveedor == null) {
            throw new RuntimeException("Proveedor no encontrado con id " + idProveedor);
        }

        return mapToProveedorResponse(proveedor);
    }

    //Actualizar un proveedor
    public ProveedorResponse updateProveedor(String idProveedor, ProveedorRequest proveedorRequest) {
        // Buscar el proveedor por id
        Proveedor proveedor = proveedorRepository.findByIdProveedor(idProveedor);
        if (proveedor == null) {
            throw new RuntimeException("Proveedor no encontrado con id " + idProveedor);
        }

        // Actualizar los campos del proveedor con los nuevos valores
        proveedor.setNombreProveedor(proveedorRequest.getNombreProveedor());
        proveedor.setRuc(proveedorRequest.getRuc());
        proveedor.setDireccion(proveedorRequest.getDireccion());
        proveedor.setCelular(proveedorRequest.getCelular());
        proveedor.setEstadoProveedor(proveedorRequest.getEstadoProveedor());

        // Guardar el proveedor actualizado
        proveedorRepository.save(proveedor);

        // Convertir y devolver la respuesta
        return mapToProveedorResponse(proveedor);
    }

    //Eliminar proveedor
    // Método para eliminar proveedor
    public boolean deleteProveedor(String idProveedor) {
        // Verifica si el proveedor existe
        if (proveedorRepository.existsById(idProveedor)) {
            proveedorRepository.deleteById(idProveedor);
            return true;
        }
        return false;
    }

    //Obtener proveedores para insumos
    public List<Proveedor> getProveedores(String globalFilter) {
        if (globalFilter == null || globalFilter.isEmpty()) {
            return proveedorRepository.findAll().stream().limit(8).toList();
        }
        return proveedorRepository.findByGlobalFilter(globalFilter).stream().limit(8).toList();
    }


}
