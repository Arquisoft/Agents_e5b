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
    private AgentDAO citizenDAO;

    @Override
    public AgentMin getAgentInfo(String login, String password) {//TODO - Renombrar a getAgentInfo
       Agent c = citizenDAO.getAgent(login, password);
       if(c != null){
    	   return new AgentMin(c.getNombre(), c.getTipoCodigo(), c.getId(), c.getEmail());
       }
       return null;
    }

    @Override
    public Agent changeInfo(Agent updatedData) {
        return citizenDAO.updateInfo(updatedData);
    }
}
