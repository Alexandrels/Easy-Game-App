package w2.com.br.easy_game_app.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

;

public class Local implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nomeLocal;
	private String endereco;
	private String proprietario;

	public Local() {
	}

	public Local(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeLocal() {
		return nomeLocal;
	}

	public void setNomeLocal(String nomeLocal) {
		this.nomeLocal = nomeLocal;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Local other = (Local) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject builder = new JSONObject();
		if (getId() != null) {
			builder.put("id", getId());
		}
		builder.put("nomeLocal", getNomeLocal()).put("endereco", getEndereco()).put("proprietario", getProprietario());

		return builder;
	}

	public Local toLocal(JSONObject jsonObject) throws JSONException {
		Local local = new Local();
		if (jsonObject.has("id")) {
			int idInt = jsonObject.getInt("id");
			local.setId(Long.valueOf(idInt));
		}
		local.setNomeLocal(jsonObject.getString("nomeLocal"));
		local.setEndereco(jsonObject.getString("endereco"));
		local.setProprietario(jsonObject.getString("proprietario"));
		return local;
	}

}
