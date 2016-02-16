package w2.com.br.easy_game_app.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import w2.com.br.easy_game_app.enuns.StatusEvento;
import w2.com.br.easy_game_app.enuns.TipoEvento;

public class Evento implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String descricao;
    private Date dataHora;
    private TipoEvento tipoEvento;

    //	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinTable(name = "evento_has_local", joinColumns = { @JoinColumn(name = "id_evento") }, inverseJoinColumns = {
//			@JoinColumnnColumn(name = "id_local") })
    private Local local;

    //	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "evento_has_equipe", joinColumns = { @JoinColumn(name = "id_evento") }, inverseJoinColumns = {
//			@JoinColumn(name = "id_equipe") })
    private List<Equipe> equipes;

    //	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinTable(name = "evento_has_notificacao", joinColumns = {
//			@JoinColumn(name = "id_evento") }, inverseJoinColumns = { @JoinColumn(name = "id_notificacao") })
    private Notificacao notificacao;

    //	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "evento_has_usuario", joinColumns = { @JoinColumn(name = "id_evento") }, inverseJoinColumns = {
//			@JoinColumn(name = "id_usuario") })
    private List<Usuario> usuarios;

    //	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "evento_has_recorrencia", joinColumns = {
//			@JoinColumn(name = "id_evento") }, inverseJoinColumns = { @JoinColumn(name = "id_recorrencia") })
    private List<Recorrencia> recorrencias = new ArrayList<Recorrencia>();

    //	@ManyToOne
//	@JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private StatusEvento statusEvento = StatusEvento.ATIVO;

    public Evento() {
        // TODO Auto-generated constructor stub
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Recorrencia> getRecorrencias() {
        return recorrencias;
    }

    public void setRecorrencias(List<Recorrencia> recorrencias) {
        this.recorrencias = recorrencias;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public StatusEvento getStatusEvento() {
        return statusEvento;
    }

    public void setStatusEvento(StatusEvento statusEvento) {
        this.statusEvento = statusEvento;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public void adicionarEquipes(List<Equipe> equipes) {
        if (getEquipes() == null) {
            setEquipes(new ArrayList<Equipe>());
        }
        getEquipes().addAll(equipes);
    }

    public void adcionarEquipe(Equipe equipe) {
        adicionarEquipes(Arrays.asList(equipe));
    }

    public void adicionarUsuarios(List<Usuario> usuarios) {
        if (getUsuarios() == null) {
            setUsuarios(new ArrayList<Usuario>());
        }
        getUsuarios().addAll(usuarios);
    }

    public void adicionarUsuario(Usuario usuario) {
        adicionarUsuarios(Arrays.asList(usuario));
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
        Evento other = (Evento) obj;
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
        builder.put("descricao", getDescricao())
                .put("dataHora", getDataHora())
                .put("tipo", getTipoEvento().ordinal()).put("usuario", getUsuario().getId())
                .put("statusEvento", getStatusEvento().ordinal());
        if (getLocal() != null) {
            // avaliar os demais ralcionamentos parecidos, que podem ou não ter
            // o relacionamento
            builder.put("local", getLocal().toJSON());
        }
        if (getEquipes() != null && !getEquipes().isEmpty()) {
            JSONArray arrayEquipes = new JSONArray();
            for (Equipe equipe : getEquipes()) {
                arrayEquipes.put(equipe.getId());
            }
            builder.put("equipes", arrayEquipes);
        }
        if (getUsuarios() == null && !getUsuarios().isEmpty()) {
            JSONArray arrayUsuarios = new JSONArray();
            for (Usuario usuario : getUsuarios()) {
                arrayUsuarios.put(usuario.getId());
            }
            builder.put("usuarios", arrayUsuarios);
        }

        return builder;
    }

    public static Evento toEvento(JSONObject jsonObject) throws JSONException {
        Evento evento = new Evento();

        if (jsonObject.has("id")) {
            evento.setId(jsonObject.getLong("id"));
        }
        evento.setDescricao(jsonObject.getString("descricao"));
        String dataHora = jsonObject.getString("dataHora");
        //criar o parde para date
        evento.setDataHora(new Date());
        evento.setUsuario(new Usuario(Long.valueOf(jsonObject.getInt("usuario"))));
        evento.setTipoEvento(TipoEvento.values()[jsonObject.getInt("tipo")]);
        evento.setLocal(new Local().toLocal(jsonObject.getJSONObject("local")));
        evento.setStatusEvento(StatusEvento.values()[jsonObject.getInt("local")]);
        JSONArray arrayEquipes = jsonObject.getJSONArray("equipes");
        for (int i = 0; i < arrayEquipes.length(); i++) {
            Equipe equipe = new Equipe(Long.valueOf(arrayEquipes.getInt((i))));
            evento.adcionarEquipe(equipe);
        }
        JSONArray arrayUsuarios = jsonObject.getJSONArray("usuarios");
        for (int i = 0; i < arrayUsuarios.length(); i++) {
            evento.adicionarUsuario(new Usuario(Long.valueOf(arrayUsuarios.getInt(i))));
        }

        return evento;

    }
}
