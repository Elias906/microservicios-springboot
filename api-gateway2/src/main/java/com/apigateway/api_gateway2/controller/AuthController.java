package com.apigateway.api_gateway2.controller;

import com.apigateway.api_gateway2.prueba.JwtUtil;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final JwtUtil jwtUtil = new JwtUtil();

    @PostMapping("/login")
    public Mono<String> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Recibiendo datos...");
        System.out.println("USERNAME: " + loginRequest.getUsername());
        System.out.println("PASSWORD: " + loginRequest.getPassword());

        // Validación de las credenciales (esto es un ejemplo, no lo hagas en producción)
        if ("user".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
            // Si es correcto, generar y devolver el token JWT
            return Mono.just(jwtUtil.generateToken(loginRequest.getUsername()));
        } else {
            // Si el usuario o la contraseña son incorrectos
            return Mono.just("Invalid credentials");
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
