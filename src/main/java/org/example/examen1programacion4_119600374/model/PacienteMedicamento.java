package org.example.examen1programacion4_119600374.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PacienteMedicamento")
public class PacienteMedicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //de uno a muchos, similar a la cardinalidad de bases de datos :))
    //n->1
    @ManyToOne
    @JoinColumn(name = "paciente", referencedColumnName = "id", nullable = false)
    private Paciente paciente;

    //igual acá jeje
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

    public Integer getDosisfavor()                 { return dosisafavor; }
    public void setDosisafavor(Integer d)           { this.dosisafavor = d; }

    public String getSelectLabel() {
        return medicamento.getLabel() + " [" + dosisafavor + "]";
    }
}