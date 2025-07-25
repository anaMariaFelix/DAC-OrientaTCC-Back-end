package dac.orientaTCC.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusPDF {

    PENDENTE("Pendente"),
    AVALIADO("Avaliado"),
    REJEITADO("Rejeitado");

    private final String descricao;

    StatusPDF(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static StatusPDF fromString(String value) {
        return StatusPDF.valueOf(value.toUpperCase());
    }
}
