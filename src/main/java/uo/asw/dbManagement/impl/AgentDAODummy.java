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
public class AgentDAODummy implements AgentDAO { //TODO - Si se quita lo de dummyAgent se podria renombrar la clase a AgentDatoImpl
    private static Agent dummyAgent;
    @PersistenceContext
    private EntityManager entityManager;
    
    static {
    	dummyAgent=new Agent("123456", "pass", "Clara Oswald", "clara@tardis.co.uk", "", "Person", 1);
        //dummyCitizen = new Agent("pass", "dummy", "123456", "Clara", "Oswald", new Date(), "clara@tardis.co.uk", "The Hyperspace", "Inglesa");
    }

    @Override
    public Agent getAgent(String login, String password, String kind) {//TODO- Actualizar para que tenga en cuenta el kind
    	@SuppressWarnings("unchecked")
    	//TODO-No hace EntityManagerFactory.createEntityManager() !! ???
		List<Agent> agent =  entityManager.createQuery(//TODO - Renombrar citizen a agent
    	        //"from Citizen where nombreUsuario = ?1 and contraseña = ?2"
				"select a from Agent a where a.identificador = ?1 and a.contraseña = ?2 and a.tipo = ?3") //TODO - Consulta sin select y sin hacer Agent a ???
    	        .setParameter(1, login).setParameter(2, password).setParameter(3, kind)
    	        .getResultList();
    	if(agent.isEmpty())
    		return null;
    	return agent.get(0);
       // return dummyAgent;
    }

    //TODO - Creo que está mal, revisar el 'todo' de abajo
    //Se esta retornando el mismo objeto que se pasa como parametro.
    //Habria que retornar el devuelto por merge, que está en estado Persistent.
    @Override
    public Agent updateInfo(Agent toUpdate) {
    	entityManager.merge(toUpdate);
        dummyAgent = toUpdate;
        return dummyAgent;
    }
    
    //TODO - Revisar esto
    //Quizás haciendo que el metodo updateInfo haga esto, no hace falta el dummyAgent
    
//    @Override
//    public Agent updateInfo(Agent toUpdate) {
//    	return entityManager.merge(toUpdate); //The merge method will return the merged object attached to the entityManager.
//    }
    
}
