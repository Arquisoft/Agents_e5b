package uo.asw.agents.service;

import uo.asw.agents.util.AgentMin;

public interface AgentsService {
	AgentMin getAgentMin(String login, String password, String kind);
	
	boolean changePassword(String login, String password, String kind, String newPassword);
	boolean changeEmail(String login, String password, String kind, String newEmail);
	boolean changeName(String login, String password, String kind, String newName);
	boolean changeKind(String login, String password, String kind, String newKind);
	boolean changeLocation(String login, String password, String kind, String newLocation);
}
