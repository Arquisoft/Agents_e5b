package uo.asw.agents.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.AgentsRepository;
import uo.asw.dbManagement.model.Agent;

@Service
public class InsertAgents {
	
	@Autowired
	private AgentsRepository agentsRepository;
	
	@SuppressWarnings("serial")
	@PostConstruct
	public void init() {
		Agent agent1 = new Agent("31668313G", "1234", "Person");
		Agent agent2 = new Agent("A58818501", "1234", "Entity");
		Agent agent3 = new Agent("525695S", "1234", "Sensor");
		
		agentsRepository.save(agent1);
		agentsRepository.save(agent2);
		agentsRepository.save(agent3);
	}
}
