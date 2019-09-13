package br.com.annahas.ultrassom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ultrassom", schema = "annahas")
public class Ultrassom implements Serializable {
	private static final long serialVersionUID = -4596465796503453763L;

	@Id
	@SequenceGenerator(name = "ULTRASSOM_CODIGO_GENERATOR", 
		sequenceName = "SQULTRASSOM", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ULTRASSOM_CODIGO_GENERATOR")
	@Column(name = "codigo")
	private BigDecimal codigo;

	@Column(name = "codigousuario")
	@NotNull
	private BigDecimal codigoUsuario;
	
	@Column(name = "datainicio")
	@NotNull
	private Calendar dataInicioReconstrucao;
	
	@Column(name = "datafim")
	@NotNull
	private Calendar dataFimReconstrucao;
	
	@Column(name = "tamanho")
	@NotNull
	private BigDecimal tamanho;
	
	@Column(name = "numeroiteracoes")
	@NotNull
	private BigDecimal numeroIteracoes;
	
	@Column(name = "url")
	@NotNull
	private String url;

	public Ultrassom() {
	}

	public Ultrassom(BigDecimal codigo, BigDecimal codigoUsuario, Calendar dataInicioReconstrucao,
			Calendar dataFimReconstrucao, BigDecimal tamanho, BigDecimal numeroIteracoes, String url) {
		super();
		this.codigo = codigo;
		this.codigoUsuario = codigoUsuario;
		this.dataInicioReconstrucao = dataInicioReconstrucao;
		this.dataFimReconstrucao = dataFimReconstrucao;
		this.tamanho = tamanho;
		this.numeroIteracoes = numeroIteracoes;
		this.url = url;
	}

	public BigDecimal getCodigo() {
		return codigo;
	}

	public void setCodigo(BigDecimal codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(BigDecimal codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public Calendar getDataInicioReconstrucao() {
		return dataInicioReconstrucao;
	}

	public void setDataInicioReconstrucao(Calendar dataInicioReconstrucao) {
		this.dataInicioReconstrucao = dataInicioReconstrucao;
	}

	public Calendar getDataFimReconstrucao() {
		return dataFimReconstrucao;
	}

	public void setDataFimReconstrucao(Calendar dataFimReconstrucao) {
		this.dataFimReconstrucao = dataFimReconstrucao;
	}

	public BigDecimal getTamanho() {
		return tamanho;
	}

	public void setTamanho(BigDecimal tamanho) {
		this.tamanho = tamanho;
	}

	public BigDecimal getNumeroIteracoes() {
		return numeroIteracoes;
	}

	public void setNumeroIteracoes(BigDecimal numeroIteracoes) {
		this.numeroIteracoes = numeroIteracoes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((codigoUsuario == null) ? 0 : codigoUsuario.hashCode());
		result = prime * result + ((dataFimReconstrucao == null) ? 0 : dataFimReconstrucao.hashCode());
		result = prime * result + ((dataInicioReconstrucao == null) ? 0 : dataInicioReconstrucao.hashCode());
		result = prime * result + ((numeroIteracoes == null) ? 0 : numeroIteracoes.hashCode());
		result = prime * result + ((tamanho == null) ? 0 : tamanho.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Ultrassom other = (Ultrassom) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (codigoUsuario == null) {
			if (other.codigoUsuario != null)
				return false;
		} else if (!codigoUsuario.equals(other.codigoUsuario))
			return false;
		if (dataFimReconstrucao == null) {
			if (other.dataFimReconstrucao != null)
				return false;
		} else if (!dataFimReconstrucao.equals(other.dataFimReconstrucao))
			return false;
		if (dataInicioReconstrucao == null) {
			if (other.dataInicioReconstrucao != null)
				return false;
		} else if (!dataInicioReconstrucao.equals(other.dataInicioReconstrucao))
			return false;
		if (numeroIteracoes == null) {
			if (other.numeroIteracoes != null)
				return false;
		} else if (!numeroIteracoes.equals(other.numeroIteracoes))
			return false;
		if (tamanho == null) {
			if (other.tamanho != null)
				return false;
		} else if (!tamanho.equals(other.tamanho))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ultrassom [codigo=" + codigo + ", codigoUsuario=" + codigoUsuario + ", dataInicioReconstrucao="
				+ dataInicioReconstrucao + ", dataFimReconstrucao=" + dataFimReconstrucao + ", tamanho=" + tamanho
				+ ", numeroIteracoes=" + numeroIteracoes + ", url=" + url + "]";
	}
	
}