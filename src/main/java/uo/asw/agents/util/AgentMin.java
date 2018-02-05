package uo.asw.agents.util;

//TODO - Averiguar para que sirve esta clase, porque creo que no se utiliza en ninún sitio salvo en los test
//y en dichos tests se hace una comprobacion sin mucho sentido. Creo que está de relleno.
public class AgentMin {
	
	private Long id;
	private String name;
	private String tipo;
	private String email;
	
	public AgentMin(Long id, String name, String tipo,
			String email) {
		this.name = name;
		this.tipo = tipo;
		this.id = id;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "AgentMin [name=" + name
				+ ", tipo=" + tipo + ", id=" + id + ", email=" + email + "]";
	}

	
	
	
	
	
	
	
}
