package uo.asw.agents.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Agent;

@Service
public class InsertAgents {
	
	@Autowired
	private AgentsService agentsService;
	
	@PostConstruct
	public void init() {
		Agent agent1 = new Agent("31668313G", "1234", "Person");
		Agent agent2 = new Agent("A58818501", "1234", "Entity");
		Agent agent3 = new Agent("525695S", "1234", "Sensor");
		
		agentsService.addAgent(agent1);
		agentsService.addAgent(agent2);
		agentsService.addAgent(agent3);
	}
}
