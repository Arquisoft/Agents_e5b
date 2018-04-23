package uo.asw.agents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import uo.asw.agents.util.AgentMin;
import uo.asw.agents.util.Check;
import uo.asw.dbManagement.AgentsRepository;
import uo.asw.dbManagement.model.Agent;
import uo.asw.parser.reader.CSVKindsReader;

@Service
public class AgentsServiceImpl implements AgentsService {

	@Autowired
	private AgentsRepository agentDAO;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public AgentMin getAgentMin(String login, String password, String kind) {
		//Recuperamos el agente de la BD
		Agent agent = getAgent(login, password,kind);
		
		if (agent != null) {
			//Si el agente existe, guardamos sus datos en un AgentMin y lo retornamos
			
			// Sacamos el kindCode del csv, usando el kind del Agent
			int kindCode = CSVKindsReader.getKindCodeByKind(agent.getKind());
			return new AgentMin(agent.getIdentifier(), agent.getName(), agent.getLocation(),
					agent.getEmail(), agent.getKind(), kindCode);
		}
		return null;
	}
	
	@Override
	public boolean changePassword(String login, String password, String kind, String newPassword) {
		Agent agent = agentDAO.getAgent(login, password, kind);
		
		if(agent != null){
			if(!newPassword.isEmpty()){
				agent.setPassword(newPassword);
				agentDAO.updateInfo(agent,agent.getIdentifier());
				return true;	
			}
		}
		return false;
	}

	@Override
	public boolean changeEmail(String login, String password, String kind, String newEmail) {
		Agent agent = agentDAO.getAgent(login, password, kind);
		
		if(agent != null){
			if(!newEmail.isEmpty() && Check.validateEmail(newEmail)){
				agent.setEmail(newEmail);
				agentDAO.updateInfo(agent,agent.getIdentifier());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean changeName(String login, String password, String kind, String newName) {
		Agent agent = agentDAO.getAgent(login, password, kind);
		
		if(agent != null){
			agent.setName(newName);
			agentDAO.updateInfo(agent,agent.getIdentifier());
			
			return true;
		}
		return false;
	}

	@Override
	public boolean changeKind(String login, String password, String kind, String newKind) {
		Agent agent = agentDAO.getAgent(login, password, kind);
		
		if(agent!=null) {
			if(!newKind.isEmpty() && Check.validateKind(newKind)){
				agent.setKind(newKind);
				agentDAO.updateInfo(agent,agent.getIdentifier());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean changeLocation(String login, String password, String kind, String newLocation) {
		Agent agent = agentDAO.getAgent(login, password, kind);
		
		if(agent!=null) {
			agent.setLocation(newLocation);
			agentDAO.updateInfo(agent,agent.getIdentifier());
			return true;
		}
		return false;
	}
	
	//Nuevos metodos
	
	@Override
	public void addAgent(Agent agent) {
		agent.setPassword(bCryptPasswordEncoder.encode(agent.getPassword()));
		agentDAO.save(agent);
	}
	
	@Override
	public Agent getAgent(String identifier, String password, String kind) {
		
		Agent agent = agentDAO.findByIdentifier(identifier);
		if(agent!=null) {
			if(bCryptPasswordEncoder.matches(password, agent.getPassword()) &&
					agent.getKind().equals(kind)) {
				return agent;
			}
		}
		return null;
	}
	
	@Override
	public String updatePassword(String password,String identifier) {
		password=bCryptPasswordEncoder.encode(password);
		agentDAO.updatePassword(password, identifier);
		return password;
	}
	
	@Override
	public void updateInfo(Agent agent,String identifier) {
		agentDAO.updateInfo(agent, identifier);
	}

}
