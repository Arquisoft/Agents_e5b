package uo.asw.participants.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uo.asw.Application;
import uo.asw.agents.service.AgentsService;
import uo.asw.dbManagement.AgentsRepository;
import uo.asw.dbManagement.model.Agent;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration

public class DBTest {

	
	@Autowired
    private AgentsService agentDAO;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Test
	/**
	 * Prueba que la información de agentes válidos es recuperada correctamente de la BD
	 * @throws Exception
	 */
    public void testGetExistingAgents() throws Exception {
    	Agent person = agentDAO.getAgent("31668313G", "1234", "Person");
    	Agent entity = agentDAO.getAgent("A58818501", "1234", "Entity");
    	Agent sensor = agentDAO.getAgent("525695S", "1234", "Sensor");

		assertEquals("31668313G", person.getIdentifier());
		//assertEquals("1234", person.getPassword());
		assertEquals(bCryptPasswordEncoder.matches("1234", person.getPassword()),true);
		assertEquals("Person", person.getKind());
		
		assertEquals("A58818501", entity.getIdentifier());
		//assertEquals("1234", entity.getPassword());
		assertEquals(bCryptPasswordEncoder.matches("1234", entity.getPassword()),true);
		assertEquals("Entity", entity.getKind());
		
		assertEquals("525695S", sensor.getIdentifier());
		//assertEquals("1234", sensor.getPassword());
		assertEquals(bCryptPasswordEncoder.matches("1234", sensor.getPassword()),true);
		assertEquals("Sensor", sensor.getKind());
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
    	agent.setPassword("newPassword");
       	agentDAO.updatePassword(agent.getPassword(),agent.getIdentifier());
        agent=agentDAO.getAgent(agent.getIdentifier(), agent.getPassword(), agent.getKind());
       	//assertEquals("newPassword", agent.getPassword());
       	assertEquals(bCryptPasswordEncoder.matches("newPassword", agent.getPassword()),true);

       	//Cambio de contraseña por la original
       	agent.setPassword("1234");
       	agentDAO.updatePassword(agent.getPassword(),agent.getIdentifier());
        agent=agentDAO.getAgent(agent.getIdentifier(), agent.getPassword(), agent.getKind());
        
       	//assertEquals("1234", agent.getPassword());
    	assertEquals(bCryptPasswordEncoder.matches("1234", agent.getPassword()),true);
    }

}