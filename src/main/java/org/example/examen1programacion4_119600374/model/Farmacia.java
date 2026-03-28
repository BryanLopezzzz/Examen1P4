package org.example.examen1programacion4_119600374.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Farmacia")
public class Farmacia {

    @Id
    @Column(length = 20, nullable = false)
    private String id;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 10, nullable = false)
    private String usuario;

    public Farmacia() {}

    public String getId()                   { return id; }
    public void setId(String id)            { this.id = id; }

    public String getNombre()               { return nombre; }
    public void setNombre(String nombre)    { this.nombre = nombre; }

    public String getUsuario()              { return usuario; }
    public void setUsuario(String usuario)  { this.usuario = usuario; }
}