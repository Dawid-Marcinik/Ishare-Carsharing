package org.ishare.app.helpers;

import javax.servlet.http.HttpSession;

import org.ishare.app.exceptions.DangerException;
import org.ishare.app.domains.Entidad;




public class H {
	/**
	 * 
	 * @param s
	 * @param mensaje
	 * @param severity info, warning o danger
	 * @param link
	 */
	public static void info(HttpSession s, String mensaje, String severity, String link) {
		s.setAttribute("mensaje", mensaje);
		s.setAttribute("severity", severity);
		s.setAttribute("link", link);
	}

	public static void info(HttpSession s, String mensaje, String severity) {
		s.setAttribute("mensaje", mensaje);
		s.setAttribute("severity", severity);
		s.setAttribute("link", "/");
	}
	
	public static void info(HttpSession s, String mensaje) {
		s.setAttribute("mensaje", mensaje);
		s.setAttribute("severity", "info");
		s.setAttribute("link", "/");
	}
	
	public static void isRolOK(String rolExigido, HttpSession s) throws DangerException {
		String rolActual = "anon";
		
		if (s.getAttribute("user") != null) {
			rolActual = ((Entidad)s.getAttribute("user")).isAdmin() ? "admin" : "auth";
		}
		System.err.println("ROL="+rolActual);

		if ((rolActual=="anon" ||  rolActual=="auth") 	&& rolExigido=="admin") {
			throw new DangerException("Rol inadecuado");
		}
		
		if ((rolActual=="anon" ) 						&& rolExigido=="auth") {
			throw new DangerException("Rol inadecuado");
		}
		
		if ((rolActual!="anon" ) 						&& rolExigido=="anon") {
			throw new DangerException("Rol inadecuado");
		}
		
		
	

	}

	
}
