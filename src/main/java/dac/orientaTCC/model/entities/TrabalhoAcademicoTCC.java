package dac.orientaTCC.model.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Version;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import dac.orientaTCC.enums.StatusTrabalho;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trabalho_academico")
public class TrabalhoAcademicoTCC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_orientador", nullable = false)
    private Orientador orientador;

    @Column(name = "nome_trabalho", nullable = false)
    private String nomeTrabalho;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @OneToMany(mappedBy = "trabalho", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Atividade> atividades;

    @OneToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusTrabalho status;

    @Version
    private Integer versao;

}
