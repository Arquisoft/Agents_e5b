package uo.asw.agents.controller.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uo.asw.agents.AgentsService;
import uo.asw.agents.controller.CitzenController;
import uo.asw.agents.util.CitizenMin;

import java.util.Map;

@RestController
public class CitzenControllerImpl implements CitzenController {

	@Autowired
	private AgentsService participantsService;

	@Override
	@RequestMapping(value = "/user", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<CitizenMin> getCitzen(@RequestBody Map<String, Object> payload) {
		//if(!payload.keySet().containsAll(Arrays.asList("login", "password"))){
		//	return new ResponseEntity<CitizenMin>(HttpStatus.BAD_REQUEST);
		//}

		String login, password;
		login = (String) payload.get("login");
		password = (String) payload.get("password");
		CitizenMin c = participantsService.getAgentInfo(login, password);
		if(c == null){
			return new ResponseEntity<CitizenMin>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CitizenMin>(c, HttpStatus.OK);

	}
	
}
