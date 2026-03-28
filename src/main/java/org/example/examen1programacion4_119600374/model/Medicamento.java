package org.example.examen1programacion4_119600374.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Medicamento")
public class Medicamento {

    @Id
    @Column(length = 20, nullable = false)
    private String id;

    @Column(length = 30, nullable = false)
    private String nombre;

    // plan = cantidad de dosis que debe comprar el paciente para recibir 1 regalo
    // Ej: plan=2 significa "2+1" → compra 2, recibe 1
    @Column
    private Integer plan;

    public Medicamento() {}

    public String getId()                   { return id; }
    public void setId(String id)            { this.id = id; }

    public String getNombre()               { return nombre; }
    public void setNombre(String nombre)    { this.nombre = nombre; }

    public Integer getPlan()                { return plan; }
    public void setPlan(Integer plan)       { this.plan = plan; }

    /**
     * Retorna el label completo para mostrar en pantalla.
     * Ej: "Taladaf 5mg (1 + 1)"
     */
    public String getLabel() {
        return nombre + " (" + plan + " + 1)";
    }
}