package uo.asw.participants.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uo.asw.Application;
import uo.asw.dbManagement.AgentDAO;
import uo.asw.dbManagement.model.Agent;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration

public class DBTest {

	
	@Autowired
    private AgentDAO agentDAO;
	
	@Test
	/**
	 * Prueba que la información de agentes válidos es recuperada correctamente de la BD
	 * @throws Exception
	 */
    public void testGetExistingAgents() throws Exception {
    	Agent person = agentDAO.getAgent("31668313G", "1234", "Person");
    	Agent entity = agentDAO.getAgent("A58818501", "1234", "Entity");
    	Agent sensor = agentDAO.getAgent("525695S", "1234", "Sensor");

		assertEquals("31668313G", person.getNombreUsuario());
		assertEquals("1234", person.getContraseña());
		assertEquals("Person", person.getTipo());
		
		assertEquals("A58818501", entity.getNombreUsuario());
		assertEquals("1234", entity.getContraseña());
		assertEquals("Entity", entity.getTipo());
		
		assertEquals("525695S", sensor.getNombreUsuario());
		assertEquals("1234", sensor.getContraseña());
		assertEquals("Sensor", sensor.getTipo());
    }
    
    @Test
    /**
	 * Agentes con login incorrecto no son retornados por la BD
	 * @throws Exception
	 */
    public void testAgentNotFoundBecauseLoginIsNotValid() throws Exception {
    	Agent person = agentDAO.getAgent("11111111H", "1234", "Person");
    	Agent entity = agentDAO.getAgent("11111111H", "1234", "Entity");
    	Agent sensor = agentDAO.getAgent("11111111H", "1234", "Sensor");

    	assertNull(person);
    	assertNull(entity);
    	assertNull(sensor);

     }
    
    @Test
    /**
	 * Agentes con password incorrecto no son retornados por la BD
	 * @throws Exception
	 */
    public void testAgentNotFoundBecausePasswordIsNotValid() throws Exception {
    	Agent person = agentDAO.getAgent("31668313G", "invalidPass", "Person");
    	Agent entity = agentDAO.getAgent("A58818501", "invalidPass", "Entity");
    	Agent sensor = agentDAO.getAgent("525695S", "invalidPass", "Sensor");
    	
    	assertNull(person);
    	assertNull(entity);
    	assertNull(sensor);
    }
    
    @Test
    /**
	 * Agentes con kind incorrecto no son retornados por la BD
	 * @throws Exception
	 */
    public void testAgentNotFoundBecauseKindIsNotValid() throws Exception {
    	Agent person = agentDAO.getAgent("31668313G", "1234", "KindNotValid");
    	Agent entity = agentDAO.getAgent("A58818501", "1234", "KindNotValid");
    	Agent sensor = agentDAO.getAgent("525695S", "1234", "KindNotValid");
    	
    	assertNull(person);
    	assertNull(entity);
    	assertNull(sensor);
    }
    
    @Test
    /**
	 * Agentes con login y password incorrectos no son retornados por la BD
	 * @throws Exception
	 */
    public void testAgentNotFoundBecauseLoginAndPasswordAreNotValid() throws Exception {
    	Agent person = agentDAO.getAgent("11111111H", "invalidPass", "Person");
    	Agent entity = agentDAO.getAgent("11111111H", "invalidPass", "Entity");
    	Agent sensor = agentDAO.getAgent("11111111H", "invalidPass", "Sensor");
    	
    	assertNull(person);
    	assertNull(entity);
    	assertNull(sensor);
    }
    
    @Test
    /**
	 * Agentes con login y kind incorrectos no son retornados por la BD
	 * @throws Exception
	 */
    public void testAgentNotFoundBecauseLoginAndKindAreNotValid() throws Exception {
    	Agent person = agentDAO.getAgent("11111111H", "1234", "KindNotValid");
    	Agent entity = agentDAO.getAgent("11111111H", "1234", "KindNotValid");
    	Agent sensor = agentDAO.getAgent("11111111H", "1234", "KindNotValid");
    	
    	assertNull(person);
    	assertNull(entity);
    	assertNull(sensor);
    }
    
    @Test
    /**
	 * Agentes con password y kind incorrectos no son retornados por la BD
	 * @throws Exception
	 */
    public void testAgentNotFoundBecausePasswordAndKindAreNotValid() throws Exception {
    	Agent person = agentDAO.getAgent("31668313G", "invalidPass", "KindNotValid");
    	Agent entity = agentDAO.getAgent("A58818501", "invalidPass", "KindNotValid");
    	Agent sensor = agentDAO.getAgent("525695S", "invalidPass", "KindNotValid");
    	
    	assertNull(person);
    	assertNull(entity);
    	assertNull(sensor);
    }
    
    @Test
    /**
	 * Agente válido al que se la actualiza la contraseña en la BD, al ser recuperado
	 * posteriormente de la BD y consultar su contraseña, tiene la contraseña nueva
	 * @throws Exception
	 */
    public void testUpdatePassword() throws Exception {
    	Agent agent = agentDAO.getAgent("31668313G", "1234", "Person");
    	
    	//Cambio de contraseña
    	agent.setContraseña("newPassword");
       	agent = agentDAO.updateInfo(agent);
       
       	assertEquals("newPassword", agent.getContraseña());
       	
       	//Cambio de contraseña por la original
       	agent.setContraseña("1234");
       	agent = agentDAO.updateInfo(agent);
       	
       	assertEquals("1234", agent.getContraseña()); 	
    }
    
    //El test original (adaptado a agents) hacia esto, es decir, comprobaba que el objeto agent que esta en estado detached
    //tiene la misma contraseña que se le acaba de poner en el metodo, lo cual obviamente se va a cumplir.
    //Deberia recuperar el objeto devuelto por updateInfo para actualizarse con el contenido de la BD.
    //Esto es lo que hago en el metodo de arriba
    
//    public void testUpdatePassword() throws Exception {
//    	Agent agent = citizenDAO.getAgent("31668313G", "1234", "Person");
//    	
//    	//Cambio de contraseña
//    	agent.setContraseña("newPassword");
//       	citizenDAO.updateInfo(agent);
//       
//       	assertEquals("newPassword", agent.getContraseña());
//       	
//       	//Cambio de contraseña por la original
//       	agent.setContraseña("1234");
//       	citizenDAO.updateInfo(agent);
//       	
//       	assertEquals("1234", agent.getContraseña(); 	
//    }

}