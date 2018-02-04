package uo.asw.agents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.agents.AgentsService;
import uo.asw.agents.util.AgentMin;
import uo.asw.dbManagement.AgentDAO;
import uo.asw.dbManagement.model.Agent;

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
    	   return new AgentMin(c.getId(), c.getNombre(), c.getTipo(), c.getEmail(),c.getTipoCodigo());
       }
       return null;
    }

    @Override
    public Agent changeInfo(Agent updatedData) {
        return agentDAO.updateInfo(updatedData);
    }
}
