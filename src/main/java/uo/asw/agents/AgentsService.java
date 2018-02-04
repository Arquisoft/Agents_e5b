package uo.asw.agents;


import uo.asw.agents.util.AgentMin;
import uo.asw.dbManagement.model.Agent;

/**
 * Servicio de participantes
 * @since 0.0.1
 */
public interface AgentsService {
   // AgentMin getAgentInfo(String login, String password);
    Agent changeInfo(Agent updatedData);
	AgentMin getAgentInfo(String login, String password, String kind);
}
