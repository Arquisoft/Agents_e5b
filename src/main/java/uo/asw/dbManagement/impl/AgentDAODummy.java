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
public class AgentDAODummy implements AgentDAO {
    private static Agent dummyAgent;
    @PersistenceContext
    private EntityManager entityManager;
    
    static {
    	dummyAgent=new Agent("123456", "pass", "Clara Oswald", "clara@tardis.co.uk", "", "Person", 1);
        //dummyCitizen = new Agent("pass", "dummy", "123456", "Clara", "Oswald", new Date(), "clara@tardis.co.uk", "The Hyperspace", "Inglesa");
    }

    @Override
    public Agent getAgent(String login, String password) {
    	@SuppressWarnings("unchecked")
		List<Agent> citizen =  entityManager.createQuery(
    	        /*"from Citizen where nombreUsuario = ?1 and contraseña = ?2"*/
				"from Agent where nombreUsuario = ?1 and contraseña = ?2")
    	        .setParameter(1, login).setParameter(2, password)
    	        .getResultList();
    	if(citizen.isEmpty())
    		return null;
    	return citizen.get(0);
       // return dummyAgent;
    }

    @Override
    public Agent updateInfo(Agent toUpdate) {
    	entityManager.merge(toUpdate);
        dummyAgent = toUpdate;
        return dummyAgent;
    }
}
