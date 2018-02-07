package uo.asw.agents.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
	
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
    /**
     * Valida el email con una expresion reglar
     * 
     * @param email
     *            email que se quiere validar
     * @return true email valido, false en caso contrario
     */
    public static boolean validateEmail(String email) {
 
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }

    /**
     * Valida el kind con unas opciones
     * 
     * @param kind
     *            tipo a validar
     * @return true tipo valido, false en caso contrario
     */
    public static boolean validateKind(String kind) {
 
      
    	return (kind.equals("Person")||kind.equals("Sensor")||kind.equals("Entity")); 
    	

    }

}
