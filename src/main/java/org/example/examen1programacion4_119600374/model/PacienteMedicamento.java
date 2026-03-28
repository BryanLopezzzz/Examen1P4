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

    public String getSelectLabel() {
        return medicamento.getLabel() + " [" + dosisafavor + "]";
    }

    public boolean puedeEntregar() {
        return dosisafavor != null
                && medicamento.getPlan() != null
                && dosisafavor >= medicamento.getPlan();
    }
}