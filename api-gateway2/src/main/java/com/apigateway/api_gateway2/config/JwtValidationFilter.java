package com.apigateway.api_gateway2.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebFilterChain;


import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtValidationFilter implements WebFilter{

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        // Permitir solicitudes OPTIONS sin validación
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return chain.filter(exchange);
        }
        System.out.println("CORS filter ejecutado para: " + exchange.getRequest().getURI());
        // Excluir rutas específicas de la validación JWT
        if (path.equals("/login") || path.equals("/api/login") || path.equals("/v1/csrf")
                || path.equals("/ws/proveedores.wsdl") || path.equals("/ws/proveedor.wsdl")
                || path.startsWith("/ws")
                || path.equals("/ws/clientes.wsdl") || path.equals("/ws/cliente.wsdl") || path.endsWith(".wsdl")
                || path.equals("/ws/proveedor?wsdl") || path.equals("/ws/cliente?wsdl")
        ) {
            System.out.println("JWT filter ejecutado para: " + path);
            return chain.filter(exchange);
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            exchange.getAttributes().put("claims", claims);

        } catch (SignatureException e) {
            System.out.println("Error en la firma del token: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } catch (Exception e) {
            System.out.println("Error desconocido en la validación del JWT: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

}
