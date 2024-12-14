package com.apigateway.cliente_service.model.dtos;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "http://www.example.com/cliente")
@Data
public class GetAllClientesResponse {

    private List<ClienteResponse> clientes = new ArrayList<>();
    private long totalElements;
    private int totalPages;

    @XmlElement
    public List<ClienteResponse> getClientes() {
        return clientes;
    }

    public void setProveedores(List<ClienteResponse> proveedores) {
        this.clientes = proveedores;
    }

    @XmlElement
    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    @XmlElement
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
