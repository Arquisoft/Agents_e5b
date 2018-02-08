package uo.asw.agents.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import uo.asw.agents.util.AgentMin;


public interface AgentController {

	/**
	 * Recibe una peticion post con la siguiente informacion en formato JSON:
	 * - login (String) 
	 * - password (String) 
	 * - kind (String) 
	 * Devuelve los datos del agente que coincida con esos 3 valores 
	 * en formato JSON o XML (según se indique), o un error (404) si no 
	 * lo encuentra
	 * 
	 * @param payload
	 * @return
	 */
	ResponseEntity<AgentMin> getAgentInfo(Map<String, Object> payload);
	
	/**
	 * Recibe una peticion post con la siguiente informacion en formato JSON:
	 * - login (String) 
	 * - password (String) 
	 * - kind (String)
	 * - infoToChange (String)
	 * - newInfo (String) 
	 * El campo infoToChange puede tomar uno de los siguientes valores:
	 * 	password, email, name, kind o location
	 * El campo newInfo contiene el nuevo valor que va a tener el campo a cambiar
	 * 
	 * Si existe un agente cuyos login, password y kind coincidan con los anteriores:
	 * Se intenta cambiar el campo indicado con el valor indicado. Si se cambia con éxito,
	 * se devuelve un HttpStatus.OK (200), si no se devuelve un error (400).
	 * 
	 * Si no existe ese agente, se devuelve un error (400).
	 * 
	 * @param payload
	 * @return
	 */
	ResponseEntity<Void> changeInfo(Map<String, Object> payload);

}
