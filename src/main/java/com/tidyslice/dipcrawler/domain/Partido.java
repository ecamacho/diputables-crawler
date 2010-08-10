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
public class Partido implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7288301266179809740L;

	@Id
	@Column(length=100)
	private String nombre;
	
	private int numeroDiputados;
	
	private int numeroIniciativas;
	
	private Integer numeroIniciativasAprobadas = 0;
	
	private Integer numeroIniciativasPendientes = 0;
	
	private Integer numeroIniciativasDesechadas = 0;

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
		synchronized (this.numeroIniciativasAprobadas) {
			this.numeroIniciativasAprobadas = numeroIniciativasAprobadas;
		}
		
	}

	public int getNumeroIniciativasPendientes() {		
		return numeroIniciativasPendientes;
	}

	public void setNumeroIniciativasPendientes(int numeroIniciativasPendientes) {
		synchronized (this.numeroIniciativasPendientes) {
			this.numeroIniciativasPendientes = numeroIniciativasPendientes;
		}
		
	}

	public int getNumeroIniciativasDesechadas() {
		return numeroIniciativasDesechadas;
	}

	public void setNumeroIniciativasDesechadas(int numeroIniciativasDesechadas) {
		synchronized (this.numeroIniciativasDesechadas) {
			this.numeroIniciativasDesechadas = numeroIniciativasDesechadas;
		}
		
	}

	public int getNumeroDiputados() {
		return numeroDiputados;
	}

	public void setNumeroDiputados(int numeroDiputados) {
		this.numeroDiputados = numeroDiputados;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Partido [nombre=");
		builder.append(nombre);
		builder.append(", numeroDiputados=");
		builder.append(numeroDiputados);
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
