package com.tidyslice.dipcrawler.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Iniciativa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 796988859703226487L;
	
	@Id
	private Long id;
		
	private String diputadoUiid;	
			
	private String titulo;
	
	private Date fecha;
	
	private String sinopsis;
	
	private String tramite;
	
	@Enumerated(EnumType.STRING)
	private RolIniciativa rolIniciativa;

	public RolIniciativa getRolIniciativa() {
		return rolIniciativa;
	}

	public void setRolIniciativa(RolIniciativa rolIniciativa) {
		this.rolIniciativa = rolIniciativa;
	}

	public String getDiputadoUiid() {
		return diputadoUiid;
	}

	public void setDiputadoUiid(String diputadoUiid) {
		this.diputadoUiid = diputadoUiid;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public String getTramite() {
		return tramite;
	}

	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Iniciativa [diputadoUiid=");
		builder.append(diputadoUiid);
		builder.append(", fecha=");
		builder.append(fecha);
		builder.append(", id=");
		builder.append(id);
		builder.append(", rolIniciativa=");
		builder.append(rolIniciativa);
		builder.append(", sinopsis=");
		builder.append(sinopsis);
		builder.append(", titulo=");
		builder.append(titulo);
		builder.append(", tramite=");
		builder.append(tramite);
		builder.append("]");
		return builder.toString();
	}

	

}
