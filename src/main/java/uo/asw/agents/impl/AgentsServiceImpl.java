package uo.asw.agents.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.agents.AgentsService;
import uo.asw.agents.util.AgentMin;
import uo.asw.dbManagement.AgentDAO;
import uo.asw.dbManagement.model.Agent;
import uo.asw.parser.reader.ExcelKindsReader;

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
    	   Map<String, Integer> paresKind_KindCode = new ExcelKindsReader().readKinds("src/main/resources/kinds.xlsx");
    	   int kindCode = paresKind_KindCode.get(c.getTipo());
    	   
    	   return new AgentMin(c.getId(), c.getNombre(), c.getLocalizacion(),c.getEmail(), c.getTipo(), kindCode);
       }
       return null;
    }

    @Override
    public Agent changeInfo(Agent updatedData) {
        return agentDAO.updateInfo(updatedData);
    }
}
