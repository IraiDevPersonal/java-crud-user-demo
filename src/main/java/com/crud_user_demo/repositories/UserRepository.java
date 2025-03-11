package com.crud_user_demo.repositories;

import com.crud_user_demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// TODO: repositorio que hereda de JpaRepository para obtener todos los metodos para la querys a la base de datos
public interface UserRepository extends JpaRepository<UserEntity, Long> {
   Optional<UserEntity> findByEmail(String email);
} 
