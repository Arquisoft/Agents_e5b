package uo.asw.agents.controller.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uo.asw.agents.controller.AgentController;
import uo.asw.agents.service.AgentsService;
import uo.asw.agents.util.AgentMin;

@Controller
public class AgentControllerImpl implements AgentController{

	@Autowired
	private AgentsService agentsService;

	@Override
	@RequestMapping(value = "/user", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<AgentMin> getAgentInfo(@RequestBody Map<String, Object> payload) {
		
		//Sacamos los parametros de la peticion
		String login, password, kind;
		login = (String) payload.get("login");
		password = (String) payload.get("password");
		kind = (String) payload.get("kind");
		
		AgentMin agentMin = agentsService.getAgentMin(login, password, kind);		
		
		if(agentMin == null){
			return new ResponseEntity<AgentMin>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AgentMin>(agentMin, HttpStatus.OK);
	}
	
	@Override
	@RequestMapping(value = "/changeInfo", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Void> changeInfo(@RequestBody Map<String, Object> payload) {
		
		//Sacamos los parametros de la peticion
		String login, password, kind, infoToChange, newInfo;
		login = (String) payload.get("login");
		password = (String) payload.get("password");
		kind = (String) payload.get("kind");
		
		infoToChange = (String) payload.get("infoToChange");
		newInfo = (String) payload.get("newInfo");
		
		boolean infoChangedCorrectly;
		
		//En funcion del tipo de informacion a cambiar, llamamos a un metodo u otro del servicio
		switch (infoToChange) {
		case "password":
			agentsService.updatePassword(password, login);
			infoChangedCorrectly = true;
			break;
			
		case "email":
			infoChangedCorrectly = agentsService.changeEmail(login, password, kind, newInfo);
			break;
		
		case "name":
			infoChangedCorrectly = agentsService.changeName(login, password, kind, newInfo);
			break;
			
		case "kind":
			infoChangedCorrectly = agentsService.changeKind(login, password, kind, newInfo);
			break;
			
		case "location":
			infoChangedCorrectly = agentsService.changeLocation(login, password, kind, newInfo);
			break;

		default:
			infoChangedCorrectly = false;
		}
				
		if(infoChangedCorrectly){
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		
    }
	
}
