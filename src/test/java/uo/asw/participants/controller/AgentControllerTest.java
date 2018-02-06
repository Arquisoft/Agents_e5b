package uo.asw.participants.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import uo.asw.Application;
import uo.asw.dbManagement.AgentDAO;
import uo.asw.dbManagement.model.Agent;
import uo.asw.parser.reader.CSVKindsReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
/**
 * Prueba la API REST
 * Plantillas para los tests extraidos de los tutoriales de Spring (https://spring.io/guides/tutorials/bookmarks/)
 * @since 0.0.1
 */
public class AgentControllerTest {

    private MockMvc mockMvc;
    @SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    private MediaType JSONContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private AgentDAO agentDAO;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    /**
     * Comprueba que el usuario se obtiene correctamente en formato JSON
     * @throws Exception
     */
    public void testGetValidUserJSON() throws Exception {
    	//Preparamos los parametros de la petición post
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
                put("login", "31668313G");
                put("password", "1234");
                put("kind", "Person");
            }
        };
        
        //Obtenemos el agente de la BD
        Agent agent = agentDAO.getAgent("31668313G", "1234", "Person");
        
        //Realizamos la petición post y comprobamos que los datos del JSON de vuelta
        //coinciden con los del agente obtenido de la BD
        
        //Dado el kind del agente, sacamos su kindCode
        int kindCode = CSVKindsReader.getKindCodeByKind(agent.getTipo());
        
        mockMvc.perform(post("/user")
                .content(this.json(payload))
                .contentType(JSONContentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['name']", is(agent.getNombre())))
                .andExpect(jsonPath("$['location']", is(agent.getLocalizacion())))
                .andExpect(jsonPath("$['email']", is(agent.getEmail())))
                .andExpect(jsonPath("$['id']", is(agent.getIdentificador())))
                .andExpect(jsonPath("$['kind']", is(agent.getTipo())))
                .andExpect(jsonPath("$['kindCode']", is(kindCode)));
    }

    @Test
    /**
     * Usuario con login no registrado
     * @throws Exception
     */
    public void testAgentNotFoundBecauseLoginIsNotValid() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
                put("login", "11111111H");
                put("password", "1234");
                put("kind", "Person");
            }
        };

        mockMvc.perform(post("/user")
        		.content(new byte[0]) //Contenido vacio
                .content(this.json(payload))
                .contentType(JSONContentType))
                .andExpect(status().isNotFound());
    }
    
    @Test
    /**
     * Usuario con password incorrecta
     * @throws Exception
     */
    public void testAgentNotFoundBecausePasswordIsNotValid() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("login", "31668313G");
                put("password", "invalidPass");
                put("kind", "Person");
            }
        };

        mockMvc.perform(post("/user")
        		.content(new byte[0]) //Contenido vacio
                .content(this.json(payload))
                .contentType(JSONContentType))
                .andExpect(status().isNotFound());
    }
    
    @Test
    /**
     * Usuario con kind incorrecto
     * @throws Exception
     */
    public void testAgentNotFoundBecauseKindIsNotValid() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("login", "31668313G");
                put("password", "1234");
                put("kind", "KindNotValid");
            }
        };

        mockMvc.perform(post("/user")
        		.content(new byte[0]) //Contenido vacio
                .content(this.json(payload))
                .contentType(JSONContentType))
                .andExpect(status().isNotFound());
    }
    
    @Test
    /**
     * Usuario con login y password incorrecto
     * @throws Exception
     */
    public void testAgentNotFoundBecauseLoginAndPasswordAreNotValid() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
                put("login", "11111111H");
                put("password", "invalidPass");
                put("kind", "Person");
            }
        };

        mockMvc.perform(post("/user")
        		.content(new byte[0]) //Contenido vacio
                .content(this.json(payload))
                .contentType(JSONContentType))
                .andExpect(status().isNotFound());
    }
    
    @Test
    /**
     * Usuario con login y kind incorrecto
     * @throws Exception
     */
    public void testAgentNotFoundBecauseLoginAndKindAreNotValid() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
                put("login", "11111111H");
                put("password", "1234");
                put("kind", "KindNotValid");
            }
        };

        mockMvc.perform(post("/user")
        		.content(new byte[0]) //Contenido vacio
                .content(this.json(payload))
                .contentType(JSONContentType))
                .andExpect(status().isNotFound());
    }
    
    @Test
    /**
     * Usuario con password y kind incorrecto
     * @throws Exception
     */
    public void testAgentNotFoundBecausePasswordAndKindAreNotValid() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
                put("login", "31668313G");
                put("password", "invalidPass");
                put("kind", "KindNotValid");
            }
        };

        mockMvc.perform(post("/user")
        		.content(new byte[0]) //Contenido vacio
                .content(this.json(payload))
                .contentType(JSONContentType))
                .andExpect(status().isNotFound());
    }
    
    @Test
    /**
     * Comprueba que el usuario se obtiene correctamente en formato XML
     * @throws Exception
     */
    public void testGetValidUserXML() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("login", "31668313G");
				put("password", "1234");
				put("kind", "Person");
            }
        };
        
        //Obtenemos el agente de la BD
        Agent agent = agentDAO.getAgent("31668313G", "1234", "Person");
        
        //Realizamos la petición post y comprobamos que los datos del XML de vuelta
        //coinciden con los del agente obtenido de la BD
        
        //Dado el kind del agente, sacamos su kindCode
        int kindCode = CSVKindsReader.getKindCodeByKind(agent.getTipo());
        String localizacion = agent.getLocalizacion() != null? agent.getLocalizacion() : "";
        
        mockMvc.perform(post("/user")
                .content(this.json(payload))
                .contentType(JSONContentType)
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk()) 
                .andExpect(xpath("//name").string(agent.getNombre()))
                .andExpect(xpath("//location").string(localizacion))
                .andExpect(xpath("//email").string(agent.getEmail()))
                .andExpect(xpath("//id").string(agent.getIdentificador()))
                .andExpect(xpath("//kind").string(agent.getTipo()))
                .andExpect(xpath("//kindCode").string(String.valueOf(kindCode)));
    }
    
    @Test
    /**
     * Comprueba que el usuario se obtiene correctamente en formato JSON
     * @throws Exception
     */
    public void testGetValidSensorJSON() throws Exception {
    	//Preparamos los parametros de la petición post
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
                put("login", "525695S");
                put("password", "1234");
                put("kind", "Sensor");
            }
        };
        
        //Obtenemos el agente de la BD
        Agent agent = agentDAO.getAgent("525695S", "1234", "Sensor");
        
        //Realizamos la petición post y comprobamos que los datos del JSON de vuelta
        //coinciden con los del agente obtenido de la BD
        
        //Dado el kind del agente, sacamos su kindCode
        int kindCode = CSVKindsReader.getKindCodeByKind(agent.getTipo());
        
        mockMvc.perform(post("/user")
                .content(this.json(payload))
                .contentType(JSONContentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['name']", is(agent.getNombre())))
                .andExpect(jsonPath("$['location']", is(agent.getLocalizacion())))
                .andExpect(jsonPath("$['email']", is(agent.getEmail())))
                .andExpect(jsonPath("$['id']", is(agent.getIdentificador())))
                .andExpect(jsonPath("$['kind']", is(agent.getTipo())))
                .andExpect(jsonPath("$['kindCode']", is(kindCode)));
    }
    
    @Test
    /**
     * Comprueba que el usuario se obtiene correctamente en formato JSON
     * @throws Exception
     */
    public void testGetValidSensorXML() throws Exception {
    	//Preparamos los parametros de la petición post
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
                put("login", "525695S");
                put("password", "1234");
                put("kind", "Sensor");
            }
        };
        
        //Obtenemos el agente de la BD
        Agent agent = agentDAO.getAgent("525695S", "1234", "Sensor");
        
        //Realizamos la petición post y comprobamos que los datos del XML de vuelta
        //coinciden con los del agente obtenido de la BD
        
      //Dado el kind del agente, sacamos su kindCode
        int kindCode = CSVKindsReader.getKindCodeByKind(agent.getTipo());
        String localizacion = agent.getLocalizacion() != null? agent.getLocalizacion() : "";
        
        mockMvc.perform(post("/user")
                .content(this.json(payload))
                .contentType(JSONContentType)
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk()) 
                .andExpect(xpath("//name").string(agent.getNombre()))
                .andExpect(xpath("//location").string(localizacion))
                .andExpect(xpath("//email").string(agent.getEmail()))
                .andExpect(xpath("//id").string(agent.getIdentificador()))
                .andExpect(xpath("//kind").string(agent.getTipo()))
                .andExpect(xpath("//kindCode").string(String.valueOf(kindCode)));
    }

    /**
     * Transforma un objeto en un string JSON
      * @param o objeto a convertir
     * @return string conteniendo el JSON
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}