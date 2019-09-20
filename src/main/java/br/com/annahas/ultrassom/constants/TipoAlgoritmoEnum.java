package br.com.annahas.ultrassom.constants;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum TipoAlgoritmoEnum {


	ALGORITMO_1(new BigDecimal(1), "Algoritmo 1"),
	ALGORITMO_2(new BigDecimal(2), "Algoritmo 2");
	
	private BigDecimal codigo;
	private String descricao;

	private static Map<BigDecimal, TipoAlgoritmoEnum> mapByCodigo = new ConcurrentHashMap<>();

	static {
		for (TipoAlgoritmoEnum enumObj : TipoAlgoritmoEnum.values()) {
			mapByCodigo.put(enumObj.codigo, enumObj);
		}
	}

	private TipoAlgoritmoEnum(BigDecimal codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public BigDecimal getCodigo() {
		return codigo;
	}

	public void setCodigo(BigDecimal codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static Map<BigDecimal, TipoAlgoritmoEnum> getMapByCodigo() {
		return mapByCodigo;
	}

	public static void setMapByCodigo(Map<BigDecimal, TipoAlgoritmoEnum> mapByCodigo) {
		TipoAlgoritmoEnum.mapByCodigo = mapByCodigo;
	}

}
