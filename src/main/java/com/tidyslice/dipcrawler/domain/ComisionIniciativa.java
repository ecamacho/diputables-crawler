/**
 * 
 */
package com.tidyslice.dipcrawler.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author erick
 *
 */
@Entity
public class ComisionIniciativa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String iniciativaId;
	
	private String comision;
	
	private int tipo;

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIniciativaId() {
		return iniciativaId;
	}

	public void setIniciativaId(String iniciativaId) {
		this.iniciativaId = iniciativaId;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ComisionIniciativa [comision=");
		builder.append(comision);
		builder.append(", id=");
		builder.append(id);
		builder.append(", iniciativaId=");
		builder.append(iniciativaId);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append("]");
		return builder.toString();
	}

	

}
