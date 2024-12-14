package com.apigateway.proveedor_service.endpoint;

import com.apigateway.proveedor_service.models.dtos.GetAllProveedoresRequest;
import com.apigateway.proveedor_service.models.dtos.GetAllProveedoresResponse;
import com.apigateway.proveedor_service.models.dtos.ProveedorResponse;
import com.apigateway.proveedor_service.services.ProveedorService;
import org.springframework.data.domain.Page;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProveedorEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/proveedor";

    private final ProveedorService proveedorService;

    public ProveedorEndpoint(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PayloadRoot(namespace = "http://www.example.com/proveedor", localPart = "getAllProveedoresRequest")
    @ResponsePayload
    public GetAllProveedoresResponse getAllProveedores(@RequestPayload GetAllProveedoresRequest request) {
        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 15;
        String filter = request.getFilter() != null ? request.getFilter() : "";

        System.out.println("REQUEST DESERIALIZADO: "+request);

        System.out.println("PAGINA PAGE: " + page);
        System.out.println("PAGINA SIZE: " + size);
        System.out.println("PAGINA FILTER: " + filter);

        Page<ProveedorResponse> proveedores = proveedorService.getAllProveedores(page, size, filter);

        System.out.println("PROVEEDORES PAGE: "+proveedores);

        GetAllProveedoresResponse response = new GetAllProveedoresResponse();
        response.getProveedores().addAll(proveedores.getContent()); // Adaptar a la respuesta de SOAP
        response.setTotalElements(proveedores.getTotalElements()); // Total de elementos
        response.setTotalPages(proveedores.getTotalPages()); // Total de páginas

        
        System.out.println("RESPUESTA DE API GETALL: "+response);

        return response;
    }
}
