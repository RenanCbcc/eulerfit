package br.com.gym.model.enums;

public enum Modo {

	CREDITO("Modo de pagamento via cartão de crédito."),
	DEBITO("Modo de pagamento via cartão de crédito."),
	BOLETO("Modo de pagamento via boleto bancário."),
	AVISTA("Modo de pagamento à vista.");

	private String descricao;

	Modo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
