package uo.asw.agents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.agents.util.AgentMin;
import uo.asw.agents.util.Check;
import uo.asw.dbManagement.AgentDAO;
import uo.asw.dbManagement.model.Agent;
import uo.asw.parser.reader.CSVKindsReader;

@Service
public class AgentsServiceImpl implements AgentsService {

	@Autowired
	private AgentDAO agentDAO;
	
	@Override
	public AgentMin getAgentMin(String login, String password, String kind) {
		//Recuperamos el agente de la BD
		Agent agent = agentDAO.getAgent(login, password,kind);
		
		if (agent != null) {
			//Si el agente existe, guardamos sus datos en un AgentMin y lo retornamos
			
			// Sacamos el kindCode del csv, usando el kind del Agent
			int kindCode = CSVKindsReader.getKindCodeByKind(agent.getTipo());
			return new AgentMin(agent.getIdentificador(), agent.getNombre(), agent.getLocalizacion(),
					agent.getEmail(), agent.getTipo(), kindCode);
		}
		return null;
	}
	
	@Override
	public boolean changePassword(String login, String password, String kind, String newPassword) {
		Agent agent = agentDAO.getAgent(login, password, kind);
		
		if(agent != null){
			if(!newPassword.isEmpty()){
				agent.setContraseña(newPassword);
				agentDAO.updateInfo(agent);
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
				agentDAO.updateInfo(agent);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean changeName(String login, String password, String kind, String newName) {
		Agent agent = agentDAO.getAgent(login, password, kind);
		
		if(agent != null){
			agent.setNombre(newName);
			agentDAO.updateInfo(agent);
			
			return true;
		}
		return false;
	}

	@Override
	public boolean changeKind(String login, String password, String kind, String newKind) {
		Agent agent = agentDAO.getAgent(login, password, kind);
		
		if(agent!=null) {
			if(!newKind.isEmpty() && Check.validateKind(newKind)){
				agent.setTipo(newKind);
				agentDAO.updateInfo(agent);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean changeLocation(String login, String password, String kind, String newLocation) {
		Agent agent = agentDAO.getAgent(login, password, kind);
		
		if(agent!=null) {
			agent.setLocalizacion(newLocation);
			agentDAO.updateInfo(agent);
			return true;
		}
		return false;
	}

}
