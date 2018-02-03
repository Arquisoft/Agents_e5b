package uo.asw.agents.util;

public class AgentMin {
	
	private Long id;
	private String name;
	private int tipoCodigo;
	private String email;
	
	public AgentMin(String name, int tipoCodigo, Long id,
			String email) {
		super();
		this.name = name;
		this.tipoCodigo = tipoCodigo;
		this.id = id;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getTipoCodigo() {
		return tipoCodigo;
	}
	public void setTipoCodigo(int tipoCodigo) {
		this.tipoCodigo = tipoCodigo;
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
				+ ", tipoCodigo=" + tipoCodigo + ", id=" + id + ", email=" + email + "]";
	}
	
	
	
	
	
	
	
}
