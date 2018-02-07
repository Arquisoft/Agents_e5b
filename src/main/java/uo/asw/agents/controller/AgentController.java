package uo.asw.agents.controller;

import org.springframework.http.ResponseEntity;

import uo.asw.agents.util.AgentMin;

import java.util.Map;


public interface AgentController {

	public ResponseEntity<AgentMin> getAgentJson(Map<String, Object> payload);
}
