package org.example.examen1programacion4_119600374.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Paciente")
public class Paciente {

    @Id
    @Column(length = 20, nullable = false)
    private String id;          // cédula

    @Column(length = 30, nullable = false)
    private String nombre;

    public Paciente() {}

    public String getId()                   { return id; }
    public void setId(String id)            { this.id = id; }

    public String getNombre()               { return nombre; }
    public void setNombre(String nombre)    { this.nombre = nombre; }
}