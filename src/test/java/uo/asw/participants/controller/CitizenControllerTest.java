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
public class CitizenControllerTest { //TODO - Renombrar a AgentControllerTest, por ejemplo

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
    public void getUserJSON() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
                put("login", "31668313G");
                put("password", "1234");
                put("kind", "Person");
            }
        };

        Agent agent = agentDAO.getAgent("31668313G", "1234", "Person");
        mockMvc.perform(post("/user")
                .content(this.json(payload))
                .contentType(JSONContentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['email']", is(agent.getEmail()))

                );
    }

    @Test
    /**
     * Usuario con login no registrado
     * @throws Exception
     */
    public void testNotFoundLogin() throws Exception {
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
    public void testNotFoundPassword() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("login", "31668313G");
                put("password", "1234abcde");
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
    public void testNotFoundKind() throws Exception {
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
    public void testNotFoundLoginAndPassword() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
                put("login", "11111111H");
                put("password", "1234abcde");
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
    public void testNotFoundLoginAndKind() throws Exception {
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
    public void testNotFoundPasswordAndKind() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
                put("login", "31668313G");
                put("password", "1234abcde");
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
    public void getUserXML() throws Exception {
        Map<String, String> payload = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("login", "31668313G");
				put("password", "1234");
				put("kind", "Person");
            }
        };

        Agent agent = agentDAO.getAgent("juan", "1234", "Person");
        mockMvc.perform(post("/user")
                .content(this.json(payload))
                .contentType(JSONContentType)
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(xpath("//email").string(agent.getEmail())
                );
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