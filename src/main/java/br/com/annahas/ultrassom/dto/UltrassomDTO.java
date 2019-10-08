package br.com.annahas.ultrassom.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.annahas.ultrassom.entity.Ultrassom;
import br.com.annahas.ultrassom.util.UltrassomUtil;
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
	
	@ApiModelProperty(value = "Tamanho da altura da imagem em pixels.", dataType = "int")
	private BigDecimal altura;
	
	@ApiModelProperty(value = "Tamanho da largura da imagem em pixels.", dataType = "int")
	private BigDecimal largura;
	
	@ApiModelProperty(value = "Número de iterações realizadas para reconstruir a imagem.", dataType = "int")
	private BigDecimal numeroIteracoes;
	
	@ApiModelProperty(value = "Algoritmo utilizado.", dataType = "int")
	private BigDecimal algoritmo;
	
	@ApiModelProperty(value = "Base64 da imagem.")
	private String imagem;
	
	public UltrassomDTO() {
	}
	
	public UltrassomDTO(BigDecimal codigo, BigDecimal codigoUsuario, Calendar dataInicioReconstrucao,
			Calendar dataFimReconstrucao, BigDecimal altura, BigDecimal largura, BigDecimal numeroIteracoes,
			BigDecimal algoritmo) {
		super();
		this.codigo = codigo;
		this.codigoUsuario = codigoUsuario;
		this.dataInicioReconstrucao = dataInicioReconstrucao;
		this.dataFimReconstrucao = dataFimReconstrucao;
		this.altura = altura;
		this.largura = largura;
		this.numeroIteracoes = numeroIteracoes;
		this.algoritmo = algoritmo;
	}

	public UltrassomDTO(Ultrassom ultrassom) {
		this.codigo = ultrassom.getCodigo();
		this.codigoUsuario = ultrassom.getCodigoUsuario();
		this.dataInicioReconstrucao = ultrassom.getDataInicioReconstrucao();
		this.dataFimReconstrucao = ultrassom.getDataFimReconstrucao();
		this.altura = ultrassom.getAltura();
		this.largura = ultrassom.getLargura();
		this.algoritmo = ultrassom.getCodigoTipoAlgoritmo();
		this.numeroIteracoes = ultrassom.getNumeroIteracoes();
		try {
			this.imagem = UltrassomUtil.generateFotoBase64(ultrassom.getImagem());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public BigDecimal getAltura() {
		return altura;
	}

	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	public BigDecimal getLargura() {
		return largura;
	}

	public void setLargura(BigDecimal largura) {
		this.largura = largura;
	}

	public BigDecimal getNumeroIteracoes() {
		return numeroIteracoes;
	}

	public void setNumeroIteracoes(BigDecimal numeroIteracoes) {
		this.numeroIteracoes = numeroIteracoes;
	}

	public BigDecimal getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(BigDecimal algoritmo) {
		this.algoritmo = algoritmo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((algoritmo == null) ? 0 : algoritmo.hashCode());
		result = prime * result + ((altura == null) ? 0 : altura.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((codigoUsuario == null) ? 0 : codigoUsuario.hashCode());
		result = prime * result + ((dataFimReconstrucao == null) ? 0 : dataFimReconstrucao.hashCode());
		result = prime * result + ((dataInicioReconstrucao == null) ? 0 : dataInicioReconstrucao.hashCode());
		result = prime * result + ((largura == null) ? 0 : largura.hashCode());
		result = prime * result + ((numeroIteracoes == null) ? 0 : numeroIteracoes.hashCode());
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
		if (algoritmo == null) {
			if (other.algoritmo != null)
				return false;
		} else if (!algoritmo.equals(other.algoritmo))
			return false;
		if (altura == null) {
			if (other.altura != null)
				return false;
		} else if (!altura.equals(other.altura))
			return false;
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
		if (largura == null) {
			if (other.largura != null)
				return false;
		} else if (!largura.equals(other.largura))
			return false;
		if (numeroIteracoes == null) {
			if (other.numeroIteracoes != null)
				return false;
		} else if (!numeroIteracoes.equals(other.numeroIteracoes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UltrassomDTO [codigo=" + codigo + ", codigoUsuario=" + codigoUsuario + ", dataInicioReconstrucao="
				+ dataInicioReconstrucao + ", dataFimReconstrucao=" + dataFimReconstrucao + ", altura=" + altura
				+ ", largura=" + largura + ", numeroIteracoes=" + numeroIteracoes + ", algoritmo=" + algoritmo + "]";
	}
	
}
