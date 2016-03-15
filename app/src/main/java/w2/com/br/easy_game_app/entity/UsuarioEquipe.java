/**
 *
 */
package w2.com.br.easy_game_app.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

import w2.com.br.easy_game_app.enuns.SimNao;
import w2.com.br.easy_game_app.enuns.TipoPosicao;

/**
 * @author Alexandre
 */
public class UsuarioEquipe implements Serializable {

    private Long id;
    //	@ManyToOne
//	@JoinColumn(name = "id_usuario")
    private Usuario usuario;
    //	@ManyToOne
//	@JoinColumn(name = "id_equipe")
    private Equipe equipe;
    private TipoPosicao posicao;
    private Date dataContratacao;
    private SimNao pendente = SimNao.SIM;
    private Date dataConvite;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public TipoPosicao getPosicao() {
        return posicao;
    }

    public void setPosicao(TipoPosicao posicao) {
        this.posicao = posicao;
    }

    public Date getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SimNao getPendente() {
        return pendente;
    }

    public void setPendente(SimNao pendente) {
        this.pendente = pendente;
    }

    public Date getDataConvite() {
        return dataConvite;
    }

    public void setDataConvite(Date dataConvite) {
        this.dataConvite = dataConvite;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((equipe == null) ? 0 : equipe.hashCode());
        result = prime * result + ((posicao == null) ? 0 : posicao.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        return result;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject builder = new JSONObject();
        if (getId() != null) {
            builder.put("id", getId());
        }
        builder.put("usuario", getUsuario().getId());
        if (getEquipe().getId() != null) {
            builder.put("equipe", getEquipe().getId());
        }
        builder.put("posicao", getPosicao().ordinal()).put("dataContratacao", getDataContratacao());

        return builder;
    }

	public static UsuarioEquipe toUsuarioEquipe(JSONObject jsonObject) throws JSONException {
		UsuarioEquipe usuarioEquipe = new UsuarioEquipe();
		if (jsonObject.has("id")) {
			usuarioEquipe.setId(Long.valueOf(jsonObject.getInt("id")));
		}
		usuarioEquipe.setUsuario(new Usuario(Long.valueOf(jsonObject.getInt("usuario"))));
        usuarioEquipe.getUsuario().setNome(jsonObject.getString("usuarioNome"));
		if (jsonObject.has("equipe")) {
			usuarioEquipe.setEquipe(new Equipe(Long.valueOf(jsonObject.getInt("equipe"))));
            usuarioEquipe.getEquipe().setNome(jsonObject.getString("equipeNome"));
		}
        //data da contratação somente quando o cara aceitar o convite
//		String dataContratacao = jsonObject.getString("dataContratacao");
		usuarioEquipe.setPosicao(TipoPosicao.values()[jsonObject.getInt("posicao")]);
        //falta tratar o parse de string para date
		usuarioEquipe.setDataContratacao(new Date());
		return usuarioEquipe;

	}

    public String getRetornaPosicao(){
        return getPosicao().getDescricao();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof UsuarioEquipe))
            return false;
        UsuarioEquipe other = (UsuarioEquipe) obj;
        if (equipe == null) {
            if (other.equipe != null)
                return false;
        } else if (!equipe.equals(other.equipe))
            return false;
        if (posicao != other.posicao)
            return false;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        return true;
    }

}
