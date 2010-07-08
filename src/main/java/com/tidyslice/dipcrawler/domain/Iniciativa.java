package com.tidyslice.dipcrawler.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
	private String id;
		
	private String diputadoUiid;	
			
	@Column(length=1000)
	private String titulo;
	
	private Date fecha;
	
	@Column(length=10000)
	private String sinopsis;
	
	@Enumerated(EnumType.STRING)
	private EstatusIniciativa tramite;
	
	@Enumerated(EnumType.STRING)
	private RolIniciativa rolIniciativa;
	
	private Date fechaPublicacion;
	
	private Date fechaAprobacion;
	
	private String linkGaceta;

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

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public void setTramite(EstatusIniciativa tramite) {
		this.tramite = tramite;
	}

	public String getLinkGaceta() {
		return linkGaceta;
	}

	public void setLinkGaceta(String linkGaceta) {
		this.linkGaceta = linkGaceta;
	}

	public EstatusIniciativa getTramite() {
		return tramite;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Iniciativa [diputadoUiid=");
		builder.append(diputadoUiid);
		builder.append(", fecha=");
		builder.append(fecha);
		builder.append(", fechaAprobacion=");
		builder.append(fechaAprobacion);
		builder.append(", fechaPublicacion=");
		builder.append(fechaPublicacion);
		builder.append(", id=");
		builder.append(id);
		builder.append(", linkGaceta=");
		builder.append(linkGaceta);
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
