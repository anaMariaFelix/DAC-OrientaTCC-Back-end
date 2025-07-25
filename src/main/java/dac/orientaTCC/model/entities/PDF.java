package dac.orientaTCC.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PDF {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "nome_arquivo", nullable = false)
    private String nomeArquivo;

    @Lob
    @Column(name = "conteudo", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] conteudo;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    @JsonBackReference
    private Atividade atividade;

}
