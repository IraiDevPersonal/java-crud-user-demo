package com.crud_user_demo.controllers;

import com.crud_user_demo.utils.JwtUtil;
import com.crud_user_demo.entities.UserEntity;
import com.crud_user_demo.entities.PhoneEntity;
import com.crud_user_demo.repositories.UserRepository;
import com.crud_user_demo.exceptions.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private final int MAX_PASSWORD_LENGTH = 6;
     @Autowired
    private JwtUtil jwtUtil; 

    @GetMapping
    public ResponseEntity<?> getUsers() {
        try {
            return ResponseEntity.ok(userRepository.findAll());
        } catch (Exception ex) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        try {
            String email = user.getEmail();
            String password = user.getPassword();

            if (checkEmailExist(email)) {
                throw new Exception("Error correo ya registrado");
            }

            if (!isValidEmail(email)) {
                throw new Exception("Error Correo invalido");
            }

            if (!isValidPassword(password)) {
                throw new Exception("Contraseña debe contener letras y numeros");
            }

            if (password.length() < MAX_PASSWORD_LENGTH) {
                throw new Exception("Contraseña debe tener un minimo de " + MAX_PASSWORD_LENGTH + " caracteres");
            }

            // TODO: para agregar los telefonos enviados en la peticion
            if (user.getPhones() != null) {
                for (PhoneEntity phone : user.getPhones()) {
                    phone.setUser(user);
                }
            }

            UserEntity savedUser = userRepository.save(user);

            Map<String, Object> response = createUserResponseAdapter(savedUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage()));
        }
    }

    private Map<String, Object> createUserResponseAdapter(UserEntity user) {
        String token = jwtUtil.generateToken(user.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("password", user.getPassword());
        response.put("phones", user.getPhones());
        response.put("createdAt", user.getCreatedAt());
        response.put("modifiedAt", user.getModifiedAt());
        response.put("lastLogin", user.getLastLogin());
        response.put("active", user.isActive());
        response.put("token", token);

        return response;
    }

    private boolean checkEmailExist(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d).+$"; // regex para verificar que por lo menos contenga numero y letros
        return Pattern.matches(passwordRegex, password);
    }
}