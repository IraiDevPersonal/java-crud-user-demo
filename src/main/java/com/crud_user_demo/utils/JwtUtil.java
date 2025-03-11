package com.crud_user_demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    // TODO: para terminos de la demo que se esta creando se dejara asi, pero esto deberia obtenerse de una variblea de entorno
    private final String SECRET_KEY = "EstaEsUnaClaveUltraSecretaDeMasDe32Caracteres";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // determinar la duracion del token 2 horas

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setId(UUID.randomUUID().toString()) // ID unico del token
                .setIssuedAt(new Date()) // fecha de creacion
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // expiracion
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // irma con clave secreta
                .compact();
    }
    
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
