package com.apigateway.cliente_service.services;

import com.apigateway.cliente_service.model.dtos.ClienteRequest;
import com.apigateway.cliente_service.model.dtos.ClienteResponse;
import com.apigateway.cliente_service.model.entities.Cliente;
import com.apigateway.cliente_service.repositories.ClienteRepository;
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
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public String addCliente(ClienteRequest clienteRequest){

        // Verificar si ya existe un cliente con el mismo nroDocumento
        if(clienteRepository.existsByNroDocumento(clienteRequest.getNroDocumento())){
            System.out.println("HOLA REPETIDO");
            return "El cliente con número de documento " + clienteRequest.getNroDocumento() + " ya existe.";
        }

        LocalDateTime fechaRegistro = (clienteRequest.getFechaRegistro() != null) ? clienteRequest.getFechaRegistro() : LocalDateTime.now();
        LocalDateTime fechaEditado = (clienteRequest.getFechaEditado() != null) ? clienteRequest.getFechaEditado() : LocalDateTime.now();

        var cliente = Cliente.builder()
                .tipoCliente(clienteRequest.getTipoCliente())
                .tipoDocumento(clienteRequest.getTipoDocumento())
                .distrito(clienteRequest.getDistrito())
                .nombres(clienteRequest.getNombres())
                .apellidoPaterno(clienteRequest.getApellidoPaterno())
                .apellidoMaterno(clienteRequest.getApellidoMaterno())
                .nroDocumento(clienteRequest.getNroDocumento())
                .direccion(clienteRequest.getDireccion())
                .celular(clienteRequest.getCelular())
                .fechaRegistro(fechaRegistro)
                .fechaEditado(fechaEditado)
                .build();

        clienteRepository.save(cliente);

        log.info("Cliente add: {}", cliente);
        return null;
    }

    public Page<ClienteResponse> getAllClientes(int page, int size, String filter){
        //var clientes = clienteRepository.findAll();
        //return clientes.stream().map(this::mapToClienteResponse).toList();

        var pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        Page<Cliente> clientes;
        if (filter.isEmpty()) {
            clientes = clienteRepository.findAll(pageRequest);
        } else {
            clientes = clienteRepository.findByFilter(filter, pageRequest);
        }

        // Convertir a ProveedorResponse
        return clientes.map(this::mapToClienteResponse);
    }

    private ClienteResponse mapToClienteResponse(Cliente cliente){
        return ClienteResponse.builder()
                .id(cliente.getId())
                .tipoCliente(cliente.getTipoCliente())
                .tipoDocumento(cliente.getTipoDocumento())
                .distrito(cliente.getDistrito())
                .nombres(cliente.getNombres())
                .apellidoPaterno(cliente.getApellidoPaterno())
                .apellidoMaterno(cliente.getApellidoMaterno())
                .nroDocumento(cliente.getNroDocumento())
                .direccion(cliente.getDireccion())
                .celular(cliente.getCelular())
                .fechaRegistro(cliente.getFechaRegistro())
                .fechaEditado(cliente.getFechaEditado())
                .build();

    }

    //Obtener un proveedor
    public ClienteResponse getClienteById(long id) {
        Cliente proveedor = clienteRepository.findById(id);
        if (proveedor == null) {
            throw new RuntimeException("Cliente no encontrado con id " + id);
        }

        return mapToClienteResponse(proveedor);
    }

    //Actualizar un cliente
    public ClienteResponse updateCliente(long id, ClienteRequest proveedorRequest) {
        // Buscar el proveedor por id
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con id " + id);
        }

        // Actualizar los campos del proveedor con los nuevos valores
        cliente.setTipoCliente(proveedorRequest.getTipoCliente());
        cliente.setTipoDocumento(proveedorRequest.getTipoDocumento());
        cliente.setDistrito(proveedorRequest.getDistrito());
        cliente.setNombres(proveedorRequest.getNombres());
        cliente.setApellidoPaterno(proveedorRequest.getApellidoPaterno());
        cliente.setApellidoMaterno(proveedorRequest.getApellidoMaterno());
        cliente.setNroDocumento(proveedorRequest.getNroDocumento());
        cliente.setDireccion(proveedorRequest.getDireccion());
        cliente.setCelular(proveedorRequest.getCelular());
        cliente.setFechaEditado(LocalDateTime.now());


        // Guardar el proveedor actualizado
        clienteRepository.save(cliente);

        // Convertir y devolver la respuesta
        return mapToClienteResponse(cliente);
    }

    //Eliminar cliente
    public boolean deleteCliente(long id) {
        // Verifica si el cliente existe
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //Obtener clientes para servicio
    public List<Cliente> getClientes(String globalFilter) {
        if (globalFilter == null || globalFilter.isEmpty()) {
            return clienteRepository.findAll().stream().limit(8).toList();
        }
        return clienteRepository.findByGlobalFilter(globalFilter).stream().limit(8).toList();
    }
}
