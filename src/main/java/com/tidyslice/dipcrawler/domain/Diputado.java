/**
 * 
 */
package com.tidyslice.dipcrawler.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * @author erick
 *
 */
@Entity

public class Diputado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7230732246116988976L;

	@Id
	private String uuid;
	
	private String nombre;
	
	private String entidad;
	
	private String distrito;
	
	private String biopicUrl;
	
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	private String email;
	
	private String partido;
	
	private String foto;

	private String tipoMayoria;
	
	private String curul;
	
	private String iniciativasUrl;
	
	private String proposicionesUrl;
	
	private String asistenciasUrl;
	
	private String votacionesUrl;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getBiopicUrl() {
		return biopicUrl;
	}

	public void setBiopicUrl(String biopicUrl) {
		this.biopicUrl = biopicUrl;
	}


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
		

	public String getTipoMayoria() {
		return tipoMayoria;
	}

	public void setTipoMayoria(String tipoMayoria) {
		this.tipoMayoria = tipoMayoria;
	}


	public String getCurul() {
		return curul;
	}

	public void setCurul(String curul) {
		this.curul = curul;
	}


	public String getIniciativasUrl() {
		return iniciativasUrl;
	}

	public void setIniciativasUrl(String iniciativasUrl) {
		this.iniciativasUrl = iniciativasUrl;
	}

	public String getProposicionesUrl() {
		return proposicionesUrl;
	}

	public void setProposicionesUrl(String proposicionesUrl) {
		this.proposicionesUrl = proposicionesUrl;
	}

	public String getAsistenciasUrl() {
		return asistenciasUrl;
	}

	public void setAsistenciasUrl(String asistenciasUrl) {
		this.asistenciasUrl = asistenciasUrl;
	}

	public String getVotacionesUrl() {
		return votacionesUrl;
	}

	public void setVotacionesUrl(String votacionesUrl) {
		this.votacionesUrl = votacionesUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Diputado [asistenciasUrl=");
		builder.append(asistenciasUrl);
		builder.append(", biopicUrl=");
		builder.append(biopicUrl);
		builder.append(", curul=");
		builder.append(curul);
		builder.append(", distrito=");
		builder.append(distrito);
		builder.append(", email=");
		builder.append(email);
		builder.append(", entidad=");
		builder.append(entidad);
		builder.append(", fechaNacimiento=");
		builder.append(fechaNacimiento);
		builder.append(", foto=");
		builder.append(foto);
		builder.append(", iniciativasUrl=");
		builder.append(iniciativasUrl);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", partido=");
		builder.append(partido);
		builder.append(", proposicionesUrl=");
		builder.append(proposicionesUrl);
		builder.append(", tipoMayoria=");
		builder.append(tipoMayoria);
		builder.append(", uuid=");
		builder.append(uuid);
		builder.append(", votacionesUrl=");
		builder.append(votacionesUrl);
		builder.append("]");
		return builder.toString();
	}

	
	
}
