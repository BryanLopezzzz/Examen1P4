package org.example.examen1programacion4_119600374.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @Column(length = 10, nullable = false)
    private String id;

    @Column(length = 100, nullable = false)
    private String clave;

    @Column(length = 10, nullable = false)
    private String rol;

    public Usuario() {}

    public String getId()                  { return id; }
    public void setId(String id)           { this.id = id; }

    public String getClave()               { return clave; }
    public void setClave(String clave)     { this.clave = clave; }

    public String getRol()                 { return rol; }
    public void setRol(String rol)         { this.rol = rol; }

}