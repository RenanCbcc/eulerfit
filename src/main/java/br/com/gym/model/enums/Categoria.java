package br.com.gym.model.enums;

public enum Categoria {
    DANÇA("Modalidade dança."),
    MUSCULAÇAO("Modalidade musculação."),
    CROSSFIT("Modalidade cross fit."),
    STEP("Modalidade dança com step.");

    private String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
