package uo.asw.agents.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import uo.asw.agents.util.AgentMin;

import java.util.Map;


public interface AgentController {

	ResponseEntity<AgentMin> getAgentInfo(Map<String, Object> payload);
	ResponseEntity<Void> changeInfo(@RequestBody Map<String, Object> payload);
}
