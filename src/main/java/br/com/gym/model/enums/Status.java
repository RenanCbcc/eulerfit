package br.com.gym.model.enums;

public enum Status {
	ATIVO("Este membro esta ativo."),
    INATIVO("Este membro esta inativo"),
    DESVINCULADO("Este membro não mais pertence à academia");
 
    private String descricao;
 
    Status(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
}
