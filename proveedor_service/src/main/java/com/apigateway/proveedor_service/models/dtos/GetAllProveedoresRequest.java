package com.apigateway.proveedor_service.models.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@XmlRootElement(namespace = "http://www.example.com/proveedor")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllProveedoresRequest {

    @XmlElement(name = "page", namespace = "http://www.example.com/proveedor")
    private Integer page;

    @XmlElement(name = "size", namespace = "http://www.example.com/proveedor")
    private Integer size;

    @XmlElement(name = "filter", namespace = "http://www.example.com/proveedor")
    private String filter;

    public Integer getPage() {
        return page; // Valor predeterminado
    }

    public void setPage(Integer page) {
        System.out.println("SetPage llamado con: " + page);
        this.page = page;
    }

    public Integer getSize() {
        return size; // Valor predeterminado
    }

    public void setSize(Integer size) {
        System.out.println("SetSize llamado con: " + size);
        this.size = size;
    }

    public String getFilter() {
        return filter; // Valor predeterminado
    }

    public void setFilter(String filter) {
        System.out.println("SetFilter llamado con: " + filter);
        this.filter = filter;
    }

}
