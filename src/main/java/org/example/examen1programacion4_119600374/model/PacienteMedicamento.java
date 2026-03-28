package org.example.examen1programacion4_119600374.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PacienteMedicamento")
public class PacienteMedicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "paciente", referencedColumnName = "id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medicamento", referencedColumnName = "id", nullable = false)
    private Medicamento medicamento;

    // Acumuladas: dosis compradas que aún no se han canjeado
    @Column
    private Integer dosisafavor;

    public PacienteMedicamento() {}

    public Integer getId()                          { return id; }
    public void setId(Integer id)                   { this.id = id; }

    public Paciente getPaciente()                   { return paciente; }
    public void setPaciente(Paciente paciente)      { this.paciente = paciente; }

    public Medicamento getMedicamento()             { return medicamento; }
    public void setMedicamento(Medicamento m)       { this.medicamento = m; }

    public Integer getDosisafavor()                 { return dosisafavor; }
    public void setDosisafavor(Integer d)           { this.dosisafavor = d; }

    /**
     * Retorna el label completo para el <select> en la vista.
     * Ej: "Taladaf 5mg (1 + 1) [0]"
     */
    public String getSelectLabel() {
        return medicamento.getLabel() + " [" + dosisafavor + "]";
    }

    /**
     * ¿Tiene suficientes acumuladas para recibir al menos 1 regalía?
     */
    public boolean puedeEntregar() {
        return dosisafavor != null
                && medicamento.getPlan() != null
                && dosisafavor >= medicamento.getPlan();
    }
}