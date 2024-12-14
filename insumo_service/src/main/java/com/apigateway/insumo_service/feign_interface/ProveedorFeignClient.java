package com.apigateway.insumo_service.feign_interface;

import com.apigateway.insumo_service.models.dtos.ProveedorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "proveedor-service")
public interface ProveedorFeignClient {

    @GetMapping("/api/proveedores/getProveedor/{idProveedor}")
    ProveedorResponse getProveedorById(@PathVariable("idProveedor") String idProveedor);
}
