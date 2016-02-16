
package w2.com.br.easy_game_app.entity;

import java.io.Serializable;
import java.util.Date;

import w2.com.br.easy_game_app.enuns.SimNao;

public class Notificacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	private Date dataHora;
	private SimNao notiifcado;

	public Notificacao() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public SimNao getNotiifcado() {
		return notiifcado;
	}

	public void setNotiifcado(SimNao notiifcado) {
		this.notiifcado = notiifcado;
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
		Notificacao other = (Notificacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
