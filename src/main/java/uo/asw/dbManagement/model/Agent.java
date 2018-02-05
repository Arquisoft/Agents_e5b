package uo.asw.dbManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "agent")
public class Agent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Column(name = "identificador", unique=true)//TODO - Sobra name = "identificador" ??
	private String identificador;
	
	@NotNull
	@Column(name = "contrasena")
	private String contraseña;
	
	@NotNull
	private String nombre;
	
	@NotNull
	private String email;
	
	private String localizacion;
	
	@NotNull
	private String tipo;
	
	
	
//	//Entiendo que esto es kindCode 
//	@NotNull
//	private int tipoCodigo;
	
	public Agent(){}
	
	

	public Agent(String nombreUsuario, String contraseña,
			String nombre, String email, String localizacion, 
			String tipo, int tipoCodigo) {
		super();
		this.identificador = nombreUsuario;
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.email = email;
		this.localizacion = localizacion;
		this.tipo = tipo;
		//this.tipoCodigo=tipoCodigo;
	}


	public String getNombreUsuario() {
		return identificador;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.identificador = nombreUsuario;
	}
	
	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLocalizacion(){
		return localizacion;
	}
	
	public void setLocalizacion(String localizacion){
		this.localizacion=localizacion;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	
//	public int getTipoCodigo(){
//		return tipoCodigo;
//	}
//	
//	public void setTipoCodigo(int tipoCodigo){
//		this.tipoCodigo=tipoCodigo;
//	}
	
	public Long getId(){
		return id;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
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
		Agent other = (Agent) obj;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Agent ["
				+ "nombreUsuario=" + identificador + ", "
				+ "contraseña=" + contraseña + ", "
				+ "nombre=" + nombre + ", "
				+ "email=" + email + ", "
				+ "localizacion=" + localizacion + ", "
				+ "tipo=" + tipo + ", "
				//+ "tipoCodigo=" + tipoCodigo
				+ "]";
				
	}
	
	

}
