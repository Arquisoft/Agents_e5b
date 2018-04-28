package uo.asw.dbManagement;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import uo.asw.dbManagement.model.Agent;

public interface AgentsRepository extends CrudRepository<Agent, Long>{
	
	@Query("SELECT a FROM Agent a WHERE a.identifier = ?1 and a.password = ?2 and a.kind = ?3")
	Agent getAgent(String login, String password, String kind);
	
	@Query("SELECT a FROM Agent a WHERE a.identifier = ?1")
	Agent findByIdentifier(String identificador);
	
	@Modifying 
	@Transactional
	@Query("UPDATE Agent a SET a.password = ?1 WHERE a.identifier = ?2")
	void updatePassword(String password, String identificador);

	@Modifying 
	@Transactional
	@Query("UPDATE Agent a SET a.email = ?1 WHERE a.identifier = ?2")
	void updateEmail(String email, String identificador);
	
	@Modifying 
	@Transactional
	@Query("UPDATE Agent a SET a.name = ?1 WHERE a.identifier = ?2")
	void updateName(String name, String identificador);
	
	@Modifying 
	@Transactional
	@Query("UPDATE Agent a SET a.location = ?1 WHERE a.identifier = ?2")
	void updateLocation(String location, String identificador);
	
	@Modifying 
	@Transactional
	@Query("UPDATE Agent a SET a.kind = ?1 WHERE a.identifier = ?2")
	void updateKind(String kind, String identificador);
}
