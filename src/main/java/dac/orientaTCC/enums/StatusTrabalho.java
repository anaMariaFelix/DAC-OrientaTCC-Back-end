package dac.orientaTCC.enums;

public enum StatusTrabalho {
    
    CONCLUIDO("Concluido"),
    EM_ANDAMENTO("Em andamento");

    private final String descricao;

    StatusTrabalho(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
