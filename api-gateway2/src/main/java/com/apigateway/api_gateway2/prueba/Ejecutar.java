package com.apigateway.api_gateway2.prueba;

public class Ejecutar {
    public static void main(String[] args){
        JwtUtil js = new JwtUtil();
        System.out.println("TOKEN GENERADO: " + js.generateToken("elias"));
    }
}
