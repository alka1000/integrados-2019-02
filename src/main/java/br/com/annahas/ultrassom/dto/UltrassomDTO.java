package br.com.annahas.ultrassom.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import br.com.annahas.ultrassom.entity.Ultrassom;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "DTO do ultrassom.")
public class UltrassomDTO implements Serializable {
	private static final long serialVersionUID = 6801551322329612970L;

	@ApiModelProperty(value = "Código identificador do ultrassom.", dataType = "int")
	private BigDecimal codigo;
	
	@ApiModelProperty(value = "Código identificador do usuário.", dataType = "int")
	private BigDecimal codigoUsuario;
	
	@ApiModelProperty(value = "Timestamp do começo da reconstrução.")
	private Calendar dataInicioReconstrucao;
	
	@ApiModelProperty(value = "Timestamp do término da reconstrução.")
	private Calendar dataFimReconstrucao;
	
	@ApiModelProperty(value = "Tamanho da imagem em pixels.", dataType = "int")
	private BigDecimal tamanho;
	
	@ApiModelProperty(value = "Número de iterações realizadas para reconstruir a imagem.", dataType = "int")
	private BigDecimal numeroIteracoes;
	
	public UltrassomDTO() {
	}
	
	public UltrassomDTO(BigDecimal codigo, BigDecimal codigoUsuario, Calendar dataInicioReconstrucao,
			Calendar dataFimReconstrucao, BigDecimal tamanho, BigDecimal numeroIteracoes) {
		super();
		this.codigo = codigo;
		this.codigoUsuario = codigoUsuario;
		this.dataInicioReconstrucao = dataInicioReconstrucao;
		this.dataFimReconstrucao = dataFimReconstrucao;
		this.tamanho = tamanho;
		this.numeroIteracoes = numeroIteracoes;
	}

	public UltrassomDTO(Ultrassom ultrassom) {
		this.codigo = ultrassom.getCodigo();
		this.codigoUsuario = ultrassom.getCodigoUsuario();
		this.dataInicioReconstrucao = ultrassom.getDataInicioReconstrucao();
		this.dataFimReconstrucao = ultrassom.getDataFimReconstrucao();
		this.tamanho = ultrassom.getTamanho();
		this.numeroIteracoes = ultrassom.getNumeroIteracoes();
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
		UltrassomDTO other = (UltrassomDTO) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "UltrassomDTO [codigo=" + codigo + ", codigoUsuario=" + codigoUsuario + ", dataInicioReconstrucao="
				+ dataInicioReconstrucao + ", dataFimReconstrucao=" + dataFimReconstrucao + ", tamanho=" + tamanho
				+ ", numeroIteracoes=" + numeroIteracoes + "]";
	}
	
}
