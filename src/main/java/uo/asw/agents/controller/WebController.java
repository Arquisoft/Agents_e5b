package uo.asw.agents.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uo.asw.agents.util.Check;
import uo.asw.dbManagement.AgentDAO;
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
		// model.addAttribute("nombre","luis");
		return "log";
	}

	// @Autowired
	// CitzenController cc;
	//
	// @RequestMapping(value = "/info", method = RequestMethod.GET, params = {
	// "user", "password" })
	// public ModelAndView showInfo(@RequestParam(value = "user") String
	// username,
	// @RequestParam(value = "password") String password) {
	// Map<String, Object> mp = new HashMap<>();
	// mp.put("login", username);
	// mp.put("password", password);
	// ResponseEntity<CitizenMin> c = cc.getCitzen(mp);
	// if(c.getStatusCode()!=HttpStatus.OK)
	// return new ModelAndView("error");
	// ModelAndView mv = new ModelAndView("view");
	// mv.addObject("name",c.getBody().getFirstName());
	// mv.addObject("mail",c.getBody().getEmail());
	// // TODO: añadir el resto de info del citizen.
	// return mv;
	// }

	@Autowired
	private AgentDAO cc;

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
			
					
			c = cc.getAgent(login, password, kind);//TODO - Mal, no debe llamar a getAgent, sino al servicio web
			
			if (c != null) {
				//TODO - esta mal
				int kindCode = CSVKindsReader.getKindCodeByKind(c.getTipo());
				
				session.setAttribute("agent",c);
				session.setAttribute("tipoCodigo", kindCode);
				model.addAttribute("resultado", "Welcome " + c.getNombre());
				return "view";
			}
		}
		return "error";

	}
	
// TODO - INTENTO DE LLAMAR AL SERVICIO WEB Y RECUPERAR LOS DATOS, PARA GUARDARLOS EN SESION EN LUGAR DE GUARDAR UN AGENT
//	/**
//	 * Recibe los datos de login del usuario, busca si exite ese usuario y en
//	 * caso de exitir pasa a la siguiente página que muestra la informacion. 
//	 * En caso contrario muestra la página de error
//	 * 
//	 * @param session
//	 *            mantiene la sesion
//	 * @param user
//	 *            nombre del login
//	 * @param password
//	 *            contresena del usuario
//	 * @param model
//	 * @return view si exito, error si fracaso
//	 */
//	@RequestMapping(value = "/info", method = RequestMethod.POST)
//	public String showInfo(HttpSession session, @RequestParam String login, @RequestParam String password, @RequestParam String kind, Model model) {
//		AgentMin agentInfo = null;
//		
//		if (login != null && password != null && kind != null) {
//			//Preparamos los parametros de la petición post
//	        Map<String, Object> payload = new HashMap<String, Object>() {
//				private static final long serialVersionUID = 1L;
//
//				{
//	                put("login", login);
//	                put("password", password);
//	                put("kind", kind);
//	            }
//	        };
//	        
//	        AgentControllerImpl agentController = new AgentControllerImpl();
//			agentInfo = agentController.getAgent(payload).getBody(); 
//			
//			if (agentInfo != null) {
//				session.setAttribute("nombre",agentInfo.getName());
//				session.setAttribute("identificador",agentInfo.getId());
//				session.setAttribute("localizacion",agentInfo.getLocation());
//				session.setAttribute("email",agentInfo.getEmail());
//				session.setAttribute("tipo",agentInfo.getKind());
//				session.setAttribute("tipocodigo",agentInfo.getKindCode());
//				model.addAttribute("resultado", "Bienvenid@ " + agentInfo.getName());
//				return "view";
//			}
//		}
//		return "error";
//
//	}
	
	
	

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
	@RequestMapping(value = "/changeInfo", method = RequestMethod.POST)
	public String changePassword(HttpSession session, @RequestParam String password, @RequestParam String newPassword,
			Model model) {

		Agent c = (Agent) session.getAttribute("agent");
		if (c != null) {
			if (c.getContraseña().equals(password) && !newPassword.isEmpty()) {
				c.setContraseña(newPassword);
				cc.updateInfo(c);
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

		Agent c = (Agent) session.getAttribute("agent");
		if(c != null){
			if(!email.isEmpty() && Check.validateEmail(email)){
				c.setEmail(email);
				cc.updateInfo(c);
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
		
		Agent c = (Agent) session.getAttribute("agent");
		if(c != null){
				c.setNombre(nombre);
				cc.updateInfo(c);
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

		Agent c = (Agent) session.getAttribute("agent");
		if(c!=null) {
			if(!kind.isEmpty() && Check.validateKind(kind)){
				c.setTipo(kind);
				cc.updateInfo(c);
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
	 * @param localization nueva localización del usuario
	 * @param model
	 * @return view si el usuario esta registrado, error si el usuario no esta registrado
	 */
	@RequestMapping(value = "/changeLocalization", method = RequestMethod.POST)
	public String changeLocalization(HttpSession session, @RequestParam String localization, Model model){

		Agent c = (Agent) session.getAttribute("agent");
		
		if(c!=null) {
			if(localization.isEmpty())
					localization="";
			
			c.setLocalizacion(localization);
			cc.updateInfo(c);
			model.addAttribute("resultado", "Agent localization updated to: " + localization);
			return "view";
		}
		return "error";
	}
	
	
}