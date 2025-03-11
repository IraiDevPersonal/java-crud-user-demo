package com.crud_user_demo.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users") // nombre de la tabla en la BD
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    // TODO: relacion de telefonos con el usuario
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PhoneEntity> phones;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_active")
    private boolean isActive = true; 

    @Column(name = "uid", unique = true, nullable = false)
    private String uid;

    // Constructor vacío necesario para JPA
    public UserEntity() {
        this.uid = UUID.randomUUID().toString();
    }

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getName() { 
        return name; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public String getEmail() { 
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    }

    public void setPassword(String password) { 
        this.password = password; 
    }

    public List<PhoneEntity> getPhones() { 
        return phones; 
    }

    public void setPhones(List<PhoneEntity> phones) { 
        this.phones = phones; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }

    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }

    public LocalDateTime getModifiedAt() { 
        return modifiedAt; 
    }

    public void setModifiedAt(LocalDateTime modifiedAt) { 
        this.modifiedAt = modifiedAt; 
    }

    // TODO: si last login por defecto al crear usuario sera igual que createdAt
    public LocalDateTime getLastLogin() { 
        return lastLogin != null ? lastLogin : createdAt; 
    }

    public void setLastLogin(LocalDateTime lastLogin) { 
        this.lastLogin = lastLogin; 
    }

    public boolean isActive() { 
        return isActive; 
    }

    public void setActive(boolean isActive) { 
        this.isActive = isActive; 
    }

    public String getUid() { 
        return uid; 
    }

    public void setUid(String uid) { 
        this.uid = uid; 
    }
}
