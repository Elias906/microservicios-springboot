package com.apigateway.proveedor_service.models.dtos;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "http://www.example.com/proveedor")
@Data
public class GetAllProveedoresResponse {

    private List<ProveedorResponse> proveedores = new ArrayList<>();
    private long totalElements;
    private int totalPages;

    @XmlElement
    public List<ProveedorResponse> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<ProveedorResponse> proveedores) {
        this.proveedores = proveedores;
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
