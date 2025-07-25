package dac.orientaTCC.enums;

public enum TipoUsuario {

    ALUNO("Aluno"),
    ORIENTADOR("Orientador"),
    COORDENADOR("Coordenador");

    private final String descricao;

    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}