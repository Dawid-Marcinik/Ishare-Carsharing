package org.ishare.app.helpers;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Entidad;
import org.ishare.app.exceptions.DangerException;

public class H {
	/**
	 *
	 * @param s
	 * @param mensaje
	 * @param severity info, warning o danger
	 * @param link
	 */
	public static void info(final HttpSession s, final String mensaje, final String severity, final String link) {
		s.setAttribute("mensaje", mensaje);
		s.setAttribute("severity", severity);
		s.setAttribute("link", link);
	}

	public static void info(final HttpSession s, final String mensaje, final String severity) {
		s.setAttribute("mensaje", mensaje);
		s.setAttribute("severity", severity);
		s.setAttribute("link", "/");
	}

	public static void info(final HttpSession s, final String mensaje) {
		s.setAttribute("mensaje", mensaje);
		s.setAttribute("severity", "info");
		s.setAttribute("link", "/");
	}

	/**
	 *
	 * @param rolExigido Tres posibilidades "anon", "auth", "admin"
	 * @param s          la sesión activa
	 * @throws DangerException si el rol no coincide con el del usuario activo
	 */
	public static void isRolOK(final String rolExigido, final HttpSession s) throws DangerException {
		String rolActual = "anon";

		if (s.getAttribute("user") != null) {
			rolActual = ((Entidad) s.getAttribute("user")).isAdmin() ? "Admin" : "User";
		}
		System.err.println("ROL=" + rolActual);

		if ((rolActual == "anon" || rolActual == "User") && rolExigido == "Admin") {
			throw new DangerException("Rol inadecuado");
		}

		if (rolActual == "anon" && rolExigido == "User") {
			throw new DangerException("Rol inadecuado");
		}

		if (rolActual != "anon" && rolExigido == "anon") {
			throw new DangerException("Rol inadecuado");
		}

	}

}
