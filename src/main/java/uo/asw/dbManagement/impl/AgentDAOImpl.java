package uo.asw.dbManagement.impl;

import org.springframework.stereotype.Repository;

import uo.asw.dbManagement.AgentDAO;
import uo.asw.dbManagement.model.Agent;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @since 0.0.1
 */
@Repository
@Transactional
public class AgentDAOImpl implements AgentDAO {
    @PersistenceContext
    private EntityManager entityManager;
   
    @Override
    public Agent getAgent(String login, String password, String kind) {
    	@SuppressWarnings("unchecked")
		List<Agent> agent =  entityManager.createQuery(
				" from Agent  where identificador = ?1 and contraseña = ?2 and tipo = ?3")
    	        .setParameter(1, login).setParameter(2, password).setParameter(3, kind)
    	        .getResultList();
    	if(agent.isEmpty())
    		return null;
    	return agent.get(0);
    }
    
    @Override
    public Agent updateInfo(Agent toUpdate) {
    	return entityManager.merge(toUpdate);
    }
    
}
