package uo.asw.agents.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uo.asw.agents.service.AgentsService;
import uo.asw.agents.util.Check;
import uo.asw.dbManagement.model.Agent;
import uo.asw.parser.reader.CSVKindsReader;

@Controller
public class WebController {

	/**
	 * Devuelve la pagina de incio login
	 * 
	 * @param model
	 * @return pagina log HTML
	 */
	@RequestMapping(value = { "/", "/portal" }, method = RequestMethod.GET)
	public String showView(Model model) {
		return "log";
	}

	@Autowired
	private AgentsService agentsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Recibe los datos de login del usuario, busca si exite ese usuario y en
	 * caso de exitir pasa a la siguiente página que muestra la informacion en
	 * caso contrario muestra la página de error
	 * 
	 * @param session
	 *            mantiene la sesion
	 * @param user
	 *            nombre del login
	 * @param password
	 *            contresena del usuario
	 * @param model
	 * @return view si exito, error si fracaso
	 */
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public String showInfo(HttpSession session, @RequestParam String login, @RequestParam String password, @RequestParam String kind, Model model) {
		Agent c = null;
		
		if (login != null && password != null && kind != null) {
			
			c = agentsService.getAgent(login, password, kind);
			
			if (c != null) {
				int kindCode = CSVKindsReader.getKindCodeByKind(c.getKind());
				
				session.setAttribute("agent",c);
				session.setAttribute("tipoCodigo", kindCode);
				model.addAttribute("resultado", "Welcome " + c.getName());
				return "view";
			}
		}
		return "error";

	}

	/**
	 * Acceso a la página que modifica los datos del usuario
	 * 
	 * @return changeInfo html para modificar datos del usuario
	 */
	@RequestMapping(value = "/changeInfo", method = RequestMethod.GET)
	public String changeInfo() {
		return "changeInfo";
	}

	
	/**
	 * Acceso a la página de información del usuario
	 * 
	 * @return view html para ver datos del usuario
	 */
	@RequestMapping(value = "/viewInfo", method = RequestMethod.GET)
	public String viewInfo() {
		return "view";
	}
	
	
	
	/**
	 * Cambia la contrasena de un usuario, siempre que el usuario este en sesion,
	 * la contrasena antigua se igual que la contrasena de parametro y la nueva
	 * contrasena no sea vacia
	 * 
	 * @param session
	 *            scope
	 * @param password
	 *            contrasena antigua
	 * @param newPassword
	 *            contrasena nueva
	 * @param model
	 * @return pagina siguiente
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(HttpSession session, @RequestParam String password, @RequestParam String newPassword,
			Model model) {

		Agent agent = (Agent) session.getAttribute("agent");
		if (agent != null) {
			if(bCryptPasswordEncoder.matches(password, agent.getPassword()) && !newPassword.isEmpty()) {
				
				agent.setPassword(newPassword);
				String newPassEncrypt=agentsService.updatePassword(agent.getPassword(),agent.getIdentifier());
				agent.setPassword(newPassEncrypt);
				model.addAttribute("resultado", "Contrasena actualizada correctamente");
				return "view";

			}
			return "errorContrasena";
		}
		return "error";

	}

	
	/**
	 * Modifica el email del usuario en sesión, comprueba que el email es correcto
	 * segun un patron y muestra el resultado sobre el HTML view, o redirige a la 
	 * pagina de error en caso de que no se encuentre el usuario en sesion
	 * @param session objeto session del usuario registrado
	 * @param email nuevo email de usuario
	 * @param model
	 * @return view si el usuario esta registrado, error si el usuario no esta registrado
	 */
	@RequestMapping(value = "/changeEmail", method = RequestMethod.POST)
	public String changeEmail(HttpSession session, @RequestParam String email, Model model){

		Agent agent = (Agent) session.getAttribute("agent");
		if(agent != null){
			if(!email.isEmpty() && Check.validateEmail(email)){
				agent.setEmail(email);
				agentsService.changeEmail(agent.getIdentifier(), agent.getPassword(), agent.getKind(), email);
				model.addAttribute("resultado", "Agent email updated to: " + email);
			}else{
				model.addAttribute("resultado", "Agent email "+ email + "not valid.");
			}
			return "view";	
		}
		return "error";
	}
	
	/**
	 * Modifica el nombre del usuario en sesión, comprueba que el email es correcto
	 * segun un patron y muestra el resultado sobre el HTML view, o redirige a la 
	 * pagina de error en caso de que no se encuentre el usuario en sesion
	 * @param session objeto session del usuario registrado
	 * @param email nuevo email de usuario
	 * @param model
	 * @return view si el usuario esta registrado, error si el usuario no esta registrado
	 */
	@RequestMapping(value = "/changeName", method = RequestMethod.POST)
	public String changeName(HttpSession session, @RequestParam String nombre, Model model){
		
		Agent agent = (Agent) session.getAttribute("agent");
		if(agent != null){
			agent.setName(nombre);
			agentsService.changeName(agent.getIdentifier(), agent.getPassword(), agent.getKind(), nombre);
			model.addAttribute("resultado", "Agent name updated to: " + nombre);
			
			return "view";	
		}
		return "error";
	}
	
	/**
	 * Modifica el kind del usuario en sesión, comprueba que el kind es correcto
	 * segun una lista y muestra el resultado sobre el HTML view, o redirige a la 
	 * pagina de error en caso de que no se encuentre el usuario en sesion
	 * @param session objeto session del usuario registrado
	 * @param kind nuevo tipo de usuario
	 * @param model
	 * @return view si el usuario esta registrado, error si el usuario no esta registrado
	 */
	@RequestMapping(value = "/changeKind", method = RequestMethod.POST)
	public String changeKind(HttpSession session, @RequestParam String kind, Model model){

		Agent agent = (Agent) session.getAttribute("agent");
		if(agent!=null) {
			if(!kind.isEmpty() && Check.validateKind(kind)){
				agent.setKind(kind);
				agentsService.changeKind(agent.getIdentifier(), agent.getPassword(), agent.getKind(), kind);
				model.addAttribute("resultado", "Agent kind updated to: " + kind);
			}else{
				model.addAttribute("resultado", "Agent kind " + kind + " not valid.");
			}
			return "view";
		}
		return "error";
	}
	
	/**
	 * Modifica la localización del usuario en sesión y muestra el resultado sobre el HTML view, o redirige a la 
	 * pagina de error en caso de que no se encuentre el usuario en sesion
	 * @param session objeto session del usuario registrado
	 * @param location nueva localización del usuario
	 * @param model
	 * @return view si el usuario esta registrado, error si el usuario no esta registrado
	 */
	@RequestMapping(value = "/changeLocation", method = RequestMethod.POST)
	public String changeLocation(HttpSession session, @RequestParam String location, Model model){

		Agent agent = (Agent) session.getAttribute("agent");
		
		if(agent!=null) {
			if(location.isEmpty())
					location="";
			
			agent.setLocation(location);
			agentsService.changeLocation(agent.getIdentifier(), agent.getPassword(), agent.getKind(), location);
			model.addAttribute("resultado", "Agent localization updated to: " + location);
			return "view";
		}
		return "error";
	}
	
	
}