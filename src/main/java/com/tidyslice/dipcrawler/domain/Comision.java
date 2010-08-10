/**
 * 
 */
package com.tidyslice.dipcrawler.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author erick
 *
 */
@Entity
public class Comision implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2672561990452776814L;

	@Id
	@Column(length=300)
	private String nombre;
	
	private int numeroIniciativas;
	
	private int numeroIniciativasAprobadas;
	
	private int numeroIniciativasPendientes;
	
	private int numeroIniciativasDesechadas;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumeroIniciativas() {
		return numeroIniciativas;
	}

	public void setNumeroIniciativas(int numeroIniciativas) {
		this.numeroIniciativas = numeroIniciativas;
	}

	public int getNumeroIniciativasAprobadas() {
		return numeroIniciativasAprobadas;
	}

	public void setNumeroIniciativasAprobadas(int numeroIniciativasAprobadas) {
		this.numeroIniciativasAprobadas = numeroIniciativasAprobadas;
	}

	public int getNumeroIniciativasPendientes() {
		return numeroIniciativasPendientes;
	}

	public void setNumeroIniciativasPendientes(int numeroIniciativasPendientes) {
		this.numeroIniciativasPendientes = numeroIniciativasPendientes;
	}

	public int getNumeroIniciativasDesechadas() {
		return numeroIniciativasDesechadas;
	}

	public void setNumeroIniciativasDesechadas(int numeroIniciativasDesechadas) {
		this.numeroIniciativasDesechadas = numeroIniciativasDesechadas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comision [nombre=");
		builder.append(nombre);
		builder.append(", numeroIniciativas=");
		builder.append(numeroIniciativas);
		builder.append(", numeroIniciativasAprobadas=");
		builder.append(numeroIniciativasAprobadas);
		builder.append(", numeroIniciativasDesechadas=");
		builder.append(numeroIniciativasDesechadas);
		builder.append(", numeroIniciativasPendientes=");
		builder.append(numeroIniciativasPendientes);
		builder.append("]");
		return builder.toString();
	}

	
}
