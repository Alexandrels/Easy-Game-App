package w2.com.br.easy_game_app.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import w2.com.br.easy_game_app.enuns.TipoPosicao;

public class Equipe implements Serializable {

	private Long id;
	private String nome;

//	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFundacao;

//	@OneToMany(mappedBy = "equipe", cascade = { CascadeType.ALL })
	private List<UsuarioEquipe> listUsuarioEquipe = new ArrayList<UsuarioEquipe>();

//	@ManyToOne
//	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Equipe() {
		// TODO Auto-generated constructor stub
	}

	public Equipe(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataFundacao() {
		return dataFundacao;
	}

	public void setDataFundacao(Date dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

	public List<UsuarioEquipe> getListUsuarioEquipe() {
		return listUsuarioEquipe;
	}

	public void setListUsuarioEquipe(List<UsuarioEquipe> listUsuarioEquipe) {
		this.listUsuarioEquipe = listUsuarioEquipe;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean naoAlterouNomeDaequipeDoUsuario(Equipe equipe) {
		if (getNome().equals(equipe.getNome()) && getUsuario().equals(equipe.usuario)) {
			return true;
		}
		return false;

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
		Equipe other = (Equipe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void adicionarUsuario(Usuario usuario, TipoPosicao posicao) {
		UsuarioEquipe usuarioEquipe = new UsuarioEquipe();
		usuarioEquipe.setDataContratacao(new Date());
		usuarioEquipe.setEquipe(this);
		usuarioEquipe.setPosicao(posicao);
		usuarioEquipe.setUsuario(usuario);
		if (!listUsuarioEquipe.contains(usuarioEquipe)) {
			listUsuarioEquipe.add(usuarioEquipe);
		}

	}

	public void adicionarUsuarioEquipe(UsuarioEquipe usuarioEquipe) {
		if (getListUsuarioEquipe() == null) {
			setListUsuarioEquipe(new ArrayList<UsuarioEquipe>());
		}
		usuarioEquipe.setEquipe(this);
		getListUsuarioEquipe().add(usuarioEquipe);
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject builder = new JSONObject();
		if (getId() != null) {
			builder.put("id", getId());
		}
		builder.put("nome", getNome()).put("dataFundacao", getDataFundacao())
				.put("usuario", getUsuario().getId());
		if (getListUsuarioEquipe() != null && !getListUsuarioEquipe().isEmpty()) {
			JSONArray arrayUsuarioEquipe = new JSONArray();
			for (UsuarioEquipe usuarioEquipe : listUsuarioEquipe) {
				arrayUsuarioEquipe.put(usuarioEquipe.toJSON());
			}
			builder.put("listaUsuarioEquipe", arrayUsuarioEquipe);
		}

		return builder;
	}

//	public static Equipe toEquipe(JsonObject jsonObject) {
//		try {
//			Equipe equipe = new Equipe();
//			if (jsonObject.containsKey("id")) {
//				equipe.setId(Long.valueOf(jsonObject.getInt("id")));
//			}
//			equipe.setNome(jsonObject.getString("nome"));
//			String dataFundacao = jsonObject.getString("dataFundacao");
//			equipe.setDataFundacao(DataUtils.parseDate(dataFundacao, "dd/MM/yyyy"));
//			equipe.setUsuario(new Usuario(Long.valueOf(jsonObject.getInt("usuario"))));
//			if (jsonObject.containsKey("listaUsuarioEquipe")) {
//				JsonArray arrayUsuarioEquipe = jsonObject.getJsonArray("listaUsuarioEquipe");
//				for (int i = 0; i < arrayUsuarioEquipe.size(); i++) {
//					JsonObject jsonUsuarioEquipe = arrayUsuarioEquipe.getJsonObject(i);
//					UsuarioEquipe usuarioEquipe = UsuarioEquipe.toUsuarioEquipe(jsonUsuarioEquipe);
//					equipe.adicionarUsuarioEquipe(usuarioEquipe);
//				}
//			}
//			return equipe;
//		} catch (JsonException e) {
//			throw new RuntimeException("Erro ao ler JSON de Usuario", e);
//		}
//
//	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Equipe [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", dataFundacao=");
		builder.append(dataFundacao);
		builder.append(", listUsuarioEquipe=");
		builder.append(listUsuarioEquipe);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append("]");
		return builder.toString();
	}

}
