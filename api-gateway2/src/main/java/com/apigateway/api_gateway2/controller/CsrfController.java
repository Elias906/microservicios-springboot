/*package com.apigateway.api_gateway2.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class CsrfController {

    @GetMapping("/csrf")
    public Map<String, String> getCsrfToken(ServerWebExchange exchange) {
        CsrfToken csrfToken = (CsrfToken) exchange.getAttribute(CsrfToken.class.getName());

        // Crear un mapa para devolverlo como JSON
        Map<String, String> response = new HashMap<>();
        if (csrfToken != null) {
            response.put("csrfToken", csrfToken.getToken());
        } else {
            response.put("message", "CSRF Token not found");
        }

        return response;
    }

}*/
