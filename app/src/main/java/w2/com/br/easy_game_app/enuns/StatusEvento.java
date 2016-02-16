package w2.com.br.easy_game_app.enuns;

public enum StatusEvento {

	ATIVO("Ativo"), 
	CANELADO("Cancelado ");

	private StatusEvento(String descricao) {
		this.descricao = descricao;
	}

	private final String descricao;

	public String getDescricao() {
		return descricao;
	}

}
