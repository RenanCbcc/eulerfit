package br.com.gym.model.enums;

public enum Tipo {
	A("Plano do tipo A"),
    B("Plano do tipo B"),
    C("Plano do tipo C"),
    D("Plano do tipo D");
 
    private String descricao;
 
    Tipo(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
}
