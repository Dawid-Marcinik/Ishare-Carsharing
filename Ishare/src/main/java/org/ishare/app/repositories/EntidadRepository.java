package org.ishare.app.repositories;

import org.ishare.app.domains.Entidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntidadRepository extends JpaRepository<Entidad, Long>{
	public Entidad getByNombreUsuario(String nombreUsuario);
}