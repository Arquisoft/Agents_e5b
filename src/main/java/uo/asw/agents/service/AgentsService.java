package uo.asw.agents.service;

import uo.asw.agents.util.AgentMin;
import uo.asw.dbManagement.model.Agent;

public interface AgentsService {

	/**
	 * Dada una combinacion de login, password y kind, devuelve un objeto de
	 * tipo AgentMin, que contiene toda la informacion necesaria de dicho
	 * agente, o null en caso de que no exista en la BD un agente con esa
	 * combinacion
	 * 
	 * @param login
	 * @param password
	 * @param kind
	 * @return
	 */
	AgentMin getAgentMin(String login, String password, String kind);

	/**
	 * Permite cambiar la contraseña de un agente
	 */
	String updatePassword(String password,String identifier);
	
	/**
	 * Permite cambiar el email de un agente cuyos login, password y kind
	 * coincidan con los pasados como parametro. Devuelve true si se ha
	 * encontrado el agente y se ha cambiado el email correctamente, y
	 * falso en caso contrario.
	 * 
	 * @param login
	 * @param password
	 * @param kind
	 * @param newEmail
	 * @return
	 */
	boolean changeEmail(String login, String password, String kind, String newEmail);
	
	/**
	 * Permite cambiar el email de un agente cuyos login, password y kind
	 * coincidan con los pasados como parametro. Devuelve true si se ha
	 * encontrado el agente y se ha cambiado el email correctamente, y
	 * falso en caso contrario.
	 * 
	 * @param login
	 * @param password
	 * @param kind
	 * @param newEmail
	 * @return
	 */
	boolean changeName(String login, String password, String kind, String newName);
	
	/**
	 * Permite cambiar el nombre de un agente cuyos login, password y kind
	 * coincidan con los pasados como parametro. Devuelve true si se ha
	 * encontrado el agente y se ha cambiado el nombre correctamente, y
	 * falso en caso contrario.
	 * 
	 * @param login
	 * @param password
	 * @param kind
	 * @param newName
	 * @return
	 */
	boolean changeKind(String login, String password, String kind, String newKind);
	
	/**
	 * Permite cambiar la localizacion de un agente cuyos login, password y kind
	 * coincidan con los pasados como parametro. Devuelve true si se ha
	 * encontrado el agente y se ha cambiado la localizacion correctamente, y
	 * falso en caso contrario.
	 * 
	 * @param login
	 * @param password
	 * @param kind
	 * @param newLocation
	 * @return
	 */
	boolean changeLocation(String login, String password, String kind, String newLocation);
	
	//NUEVOS
	
	void addAgent(Agent agent);
	Agent getAgent(String identifier, String password, String kind);
		
}
