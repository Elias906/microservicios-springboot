package com.apigateway.cliente_service.endpoint;

import com.apigateway.cliente_service.model.dtos.ClienteResponse;
import com.apigateway.cliente_service.model.dtos.GetAllClientesRequest;
import com.apigateway.cliente_service.model.dtos.GetAllClientesResponse;
import com.apigateway.cliente_service.services.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ClienteEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/cliente";

    private final ClienteService clienteService;

    public ClienteEndpoint(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PayloadRoot(namespace = "http://www.example.com/cliente", localPart = "getAllClientesRequest")
    @ResponsePayload
    public GetAllClientesResponse getAllClientes(@RequestPayload GetAllClientesRequest request) {
        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 15;
        String filter = request.getFilter() != null ? request.getFilter() : "";

        Page<ClienteResponse> clientes = clienteService.getAllClientes(page, size, filter);

        GetAllClientesResponse response = new GetAllClientesResponse();
        response.getClientes().addAll(clientes.getContent()); // Adaptar a la respuesta de SOAP
        response.setTotalElements(clientes.getTotalElements()); // Total de elementos
        response.setTotalPages(clientes.getTotalPages()); // Total de páginas


        System.out.println("RESPUESTA DE API GETALL: "+response);

        return response;
    }
}
