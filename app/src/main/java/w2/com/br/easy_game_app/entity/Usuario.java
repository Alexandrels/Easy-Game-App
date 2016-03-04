package w2.com.br.easy_game_app.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import w2.com.br.easy_game_app.enuns.SimNao;
import w2.com.br.easy_game_app.enuns.TipoPosicao;
import w2.com.br.easy_game_app.enuns.TipoUsuario;

/**
 * Created by alexandre on 05/09/15.
 */
public class Usuario implements Serializable {

    private Long id;
    private String login;
    private String senha;
    private String nome;
    private Double latitude;
    private Double longitude;
    private String tipoUsuario;
    private SimNao facebook = SimNao.NAO;
    private String apelido;
    private TipoPosicao tipoPosicao;
    private String telefone;

    //    @OneToMany(mappedBy = "usuario")
    private List<Evento> eventos;

    //    @OneToMany(mappedBy = "usuario")
    private List<Equipe> equipes;

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario() {
    }

    /*
     * um ou mais tipos
     */
    public void salvarTipoUsuario(List<TipoUsuario> tiposUsuario) {
        StringBuilder builder = new StringBuilder();
        if (tipoUsuario != null) {
            List<TipoUsuario> novosExistentesAjustados = ajustaNovosTiposUsuarioComJaExistentes(tiposUsuario);
            for (TipoUsuario tipoUsuario : novosExistentesAjustados) {
                builder.append(tipoUsuario.ordinal()).append(";");
            }
            this.tipoUsuario = builder.toString();
        } else {
            for (TipoUsuario tipoUsuario : tiposUsuario) {
                builder.append(tipoUsuario.ordinal()).append(";");
            }
            this.tipoUsuario = builder.toString();
        }
    }

    public List<TipoUsuario> ajustaNovosTiposUsuarioComJaExistentes(List<TipoUsuario> novosTiposUsuario) {
        List<TipoUsuario> tiposDoUsuario = recuperarTipoUsuario();
        for (TipoUsuario novoTipoUsuario : novosTiposUsuario) {
            if (!tiposDoUsuario.contains(novoTipoUsuario)) {
                tiposDoUsuario.add(novoTipoUsuario);
            }
        }
        return tiposDoUsuario;

    }

    /**
     * Recuperar um ou mais tipos desse usuario
     *
     * @return
     */
    public List<TipoUsuario> recuperarTipoUsuario() {
        List<TipoUsuario> tiposUsuarios = new ArrayList<TipoUsuario>();
        String[] tipo = getTipoUsuario().split(";");
        for (String string : tipo) {
            tiposUsuarios.add(TipoUsuario.values()[Integer.valueOf(string)]);
        }
        return tiposUsuarios;

    }

    public List<TipoUsuario> tiposDoUsuario() {

        return null;

    }

    public SimNao getFacebook() {
        return facebook;
    }

    public void setFacebook(SimNao facebook) {
        this.facebook = facebook;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoPosicao getTipoPosicao() {
        return tipoPosicao;
    }

    public void setTipoPosicao(TipoPosicao tipoPosicao) {
        this.tipoPosicao = tipoPosicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
        if (!(obj instanceof Usuario))
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha + ", nome=" + nome + ", latitude="
                + latitude + ", longitude=" + longitude + ", tipoUsuario=" + tipoUsuario + ", facebook=" + facebook
                + ", apelido=" + apelido + ", tipoPosicao=" + tipoPosicao + "]";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject builder = new JSONObject();
        if (getId() != null) {
            builder.put("id", getId());
        }
        builder.put("nome", getNome())
                .put("apelido", getApelido())
                .put("login", getLogin())
                .put("senha", getSenha())
                .put("latitude", getLatitude() != null ? getLatitude() : 0)
                .put("longitude", getLongitude() != null ? getLongitude() : 0)
                .put("facebook", getFacebook().ordinal())
                .put("posicao", getTipoPosicao() != null ? getTipoPosicao().ordinal() : 2)
                .put("tipoUsuario", getTipoUsuario() == null ? "1," : getTipoUsuario())
                .put("telefone", getTelefone());

        return builder;
    }

    public static Usuario toUsuario(JSONObject jsonObject) throws JSONException {
        Usuario usuario = new Usuario();
        try {
            if (jsonObject.has("id")) {
                usuario.setId(jsonObject.getLong("id"));
            }
            usuario.setNome(jsonObject.getString("nome"));
            usuario.setApelido(jsonObject.getString("apelido"));
            usuario.setFacebook(jsonObject.getInt("facebook") == 1 ? SimNao.SIM : SimNao.NAO);
            usuario.setLatitude(Double.valueOf(jsonObject.get("latitude").toString()));
            usuario.setLongitude(Double.valueOf(jsonObject.get("longitude").toString()));
            usuario.setLogin(jsonObject.getString("login"));
            usuario.setSenha(jsonObject.getString("senha"));
            usuario.setTipoPosicao(TipoPosicao.values()[jsonObject.getInt("posicao")]);
            usuario.setTipoUsuario(jsonObject.getString("tipoUsuario"));
            usuario.setTelefone(jsonObject.getString("telefone"));
        } catch (JSONException e) {
            throw new RuntimeException("Erro ao ler JSON de Usuario", e);
        }
        return usuario;

    }
    public static Usuario toCoordenadasUsuario(JSONObject jsonObject) throws JSONException {
        Usuario usuario = new Usuario();
        try {
            if (jsonObject.has("id")) {
                usuario.setId(jsonObject.getLong("id"));
            }
            usuario.setNome(jsonObject.getString("nome"));
            usuario.setApelido(jsonObject.getString("apelido"));
            usuario.setLatitude(Double.valueOf(jsonObject.get("latitude").toString()));
            usuario.setLongitude(Double.valueOf(jsonObject.get("longitude").toString()));
            usuario.setTipoPosicao(TipoPosicao.values()[jsonObject.getInt("posicao")]);
            usuario.setTelefone(jsonObject.getString("telefone"));
        } catch (JSONException e) {
            throw new RuntimeException("Erro ao ler JSON de Usuario", e);
        }
        return usuario;

    }



}
