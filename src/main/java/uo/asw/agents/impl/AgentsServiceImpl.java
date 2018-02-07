package uo.asw.agents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.agents.AgentsService;
import uo.asw.agents.util.AgentMin;
import uo.asw.dbManagement.AgentDAO;
import uo.asw.dbManagement.model.Agent;
import uo.asw.parser.reader.CSVKindsReader;

/**
 * Created by Irazusta on 15/02/2017.
 */
@Service
public class AgentsServiceImpl implements AgentsService {
    @Autowired
    private AgentDAO agentDAO;

    @Override
    public AgentMin getAgentInfo(String login, String password,String kind) {
       Agent c = agentDAO.getAgent(login, password,kind);
       if(c != null){
    	   //Sacamos el kindCode del csv, usando el kind del Agent
    	   int kindCode = CSVKindsReader.getKindCodeByKind(c.getTipo());
    	   return new AgentMin(c.getIdentificador(), c.getNombre(), c.getLocalizacion(),c.getEmail(), c.getTipo(), kindCode);
       }
       return null;
    }

    @Override
    public Agent changeInfo(Agent updatedData) {//TODO - Se le tiene que pasar una combinacion de usuario/contraseña/nuevaContraseña
        return agentDAO.updateInfo(updatedData);
    }
}
