package com.apigateway.insumo_service.services;

import com.apigateway.insumo_service.feign_interface.ProveedorFeignClient;
import com.apigateway.insumo_service.models.dtos.InsumoRequest;
import com.apigateway.insumo_service.models.dtos.InsumoResponse;
import com.apigateway.insumo_service.models.dtos.ProveedorResponse;
import com.apigateway.insumo_service.models.entities.Insumo;
import com.apigateway.insumo_service.repositories.InsumoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InsumoService {

    private final InsumoRepository insumoRepository;

    private final ProveedorFeignClient proveedorFeignClient;

    public String addInsumo(InsumoRequest insumoRequest){

        Insumo ultimoInsumo = insumoRepository.findTopByOrderByIdDesc();

        //System.out.println("INSUMO REQUEST: "+insumoRequest);

        // Generar el nuevo código
        String nuevoCodigo;
        if (ultimoInsumo != null) {
            String ultimoCodigo = ultimoInsumo.getCodigo();
            int ultimoNumero = Integer.parseInt(ultimoCodigo.substring(5));
            int nuevoNumero = ultimoNumero + 1;
            nuevoCodigo = "INSU-" + String.format("%06d", nuevoNumero);
        } else {
            nuevoCodigo = "INSU-" + String.format("%06d", 1);
        }

        // Verificar si ya existe un insumo con el mismo código
        boolean codigoExistente = insumoRepository.existsByCodigo(nuevoCodigo);
        if (codigoExistente) {
            log.info("El insumo con código {} ya existe. Intenta nuevamente.", nuevoCodigo);
            return "El insumo con código " + nuevoCodigo + " ya existe. Vuelva a intentarlo.";
        }

        var insumo = Insumo.builder()
                .categoriaInsumoId(insumoRequest.getCategoriaInsumoId())
                .proveedorId(insumoRequest.getProveedorId())
                .marcaInsumoId(insumoRequest.getMarcaInsumoId())
                .codigo(nuevoCodigo)
                .nombre(insumoRequest.getNombre())
                .descripcion(insumoRequest.getDescripcion())
                .color(insumoRequest.getColor())
                .ingreso(0)
                .salida(0)
                .saldo(0)
                .estado(false)
                .build();

        insumoRepository.save(insumo);

        log.info("Insumo add: {}", insumo);
        return null;
    }

    public Page<InsumoResponse> getAllInsumos(int page, int size, String filter){
        // var insumos = insumoRepository.findAll();
        //return insumos.stream().map(this::mapToInsumoResponse).toList();

        var pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        Page<Insumo> insumos;
        if (filter.isEmpty()) {
            insumos = insumoRepository.findAll(pageRequest);
        } else {
            insumos = insumoRepository.findByFilter(filter, pageRequest);
        }

        // Convertir a ProveedorResponse
        //return insumos.map(this::mapToInsumoResponse);
        System.out.println("INSUMOS CON PROVEEDORES?: "+insumos);

        // Mapeo de insumos a respuestas, añadiendo información del proveedor usando Feign
        return insumos.map(insumo -> {
            ProveedorResponse proveedorResponse = proveedorFeignClient.getProveedorById(insumo.getProveedorId());
            return mapToInsumoResponse2(insumo, proveedorResponse);  // Mapeamos el insumo y el proveedor
        });
    }

    private InsumoResponse mapToInsumoResponse2(Insumo insumo, ProveedorResponse proveedorResponse){
        return InsumoResponse.builder()
                .id(insumo.getId())
                .categoriaInsumoId(insumo.getCategoriaInsumoId())
                .proveedorId(insumo.getProveedorId())
                .marcaInsumoId(insumo.getMarcaInsumoId())
                .codigo(insumo.getCodigo())
                .nombre(insumo.getNombre())
                .descripcion(insumo.getDescripcion())
                .color(insumo.getColor())
                .ingreso(insumo.getIngreso())
                .salida(insumo.getSalida())
                .saldo(insumo.getSaldo())
                .estado(insumo.getEstado())
                .proveedor(proveedorResponse)
                .build();

    }

    private InsumoResponse mapToInsumoResponse(Insumo insumo){
        return InsumoResponse.builder()
                .id(insumo.getId())
                .categoriaInsumoId(insumo.getCategoriaInsumoId())
                .proveedorId(insumo.getProveedorId())
                .marcaInsumoId(insumo.getMarcaInsumoId())
                .codigo(insumo.getCodigo())
                .nombre(insumo.getNombre())
                .descripcion(insumo.getDescripcion())
                .color(insumo.getColor())
                .ingreso(insumo.getIngreso())
                .salida(insumo.getSalida())
                .saldo(insumo.getSaldo())
                .estado(insumo.getEstado())
                .build();

    }

    //Obtener un proveedor
    public InsumoResponse getInsumoById(long id) {
        Insumo insumo = insumoRepository.findById(id);
        if (insumo == null) {
            throw new RuntimeException("Insumo no encontrado con id " + id);
        }

        ProveedorResponse proveedorResponse = proveedorFeignClient.getProveedorById(insumo.getProveedorId());

        return mapToInsumoResponse2(insumo, proveedorResponse);
    }

    //Actualizar un insumo
    public InsumoResponse updateInsumo(long id, InsumoRequest insumoRequest) {
        // Buscar el proveedor por id
        Insumo insumo = insumoRepository.findById(id);
        if (insumo == null) {
            throw new RuntimeException("Insumo no encontrado con id " + id);
        }

        System.out.println("INGRESO DE PHP en SERVICIO: "+insumoRequest.getIngreso());

        // Actualizar los campos del proveedor con los nuevos valores
        insumo.setCategoriaInsumoId(insumoRequest.getCategoriaInsumoId());
        insumo.setProveedorId(insumoRequest.getProveedorId());
        insumo.setMarcaInsumoId(insumoRequest.getMarcaInsumoId());
        insumo.setNombre(insumoRequest.getNombre());
        insumo.setDescripcion(insumoRequest.getDescripcion());
        insumo.setColor(insumoRequest.getColor());

        // Asignar valores predeterminados solo si no están definidos
        if (insumoRequest.getIngreso() == null) {
            insumo.setIngreso(insumo.getIngreso() != null ? insumo.getIngreso() : 0);
        } else {
            insumo.setIngreso(insumoRequest.getIngreso());
        }

        if (insumoRequest.getSalida() == null) {
            insumo.setSalida(insumo.getSalida() != null ? insumo.getSalida() : 0);
        } else {
            insumo.setSalida(insumoRequest.getSalida());
        }

        if (insumoRequest.getSaldo() == null) {
            insumo.setSaldo(insumo.getSaldo() != null ? insumo.getSaldo() : 0);
        } else {
            insumo.setSaldo(insumoRequest.getSaldo());
        }

        if (insumoRequest.getEstado() == null) {
            insumo.setEstado(insumo.getEstado() != null ? insumo.getEstado() : false);
        } else {
            insumo.setEstado(insumoRequest.getEstado());
        }

        System.out.println("GUARDADO DE INGRESO: "+ insumo.getIngreso());

        // Guardar el proveedor actualizado
        insumoRepository.save(insumo);

        ProveedorResponse proveedorResponse = proveedorFeignClient.getProveedorById(insumo.getProveedorId());

        // Convertir y devolver la respuesta
        return mapToInsumoResponse2(insumo, proveedorResponse);
    }

    //Eliminar insumo
    public boolean deleteInsumo(long id) {
        // Verifica si el cliente existe
        if (insumoRepository.existsById(id)) {
            insumoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //Obtener insumos para movimientos insumos
    public List<Insumo> getInsumos(String globalFilter) {
        if (globalFilter == null || globalFilter.isEmpty()) {
            return insumoRepository.findAll().stream().limit(8).toList();
        }
        return insumoRepository.findByGlobalFilter(globalFilter).stream().limit(8).toList();
    }
}
