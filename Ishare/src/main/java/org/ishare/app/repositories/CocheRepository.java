package org.ishare.app.repositories;

import java.util.List;

import org.ishare.app.domains.Coche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocheRepository extends JpaRepository<Coche, Long>{

	public List<Coche> findByModelo_Marca_NombreContaining(String nombre);
	public List<Coche> findByModelo_NombreContaining(String nombre);
	public List<Coche> findByAutonomiaRestanteGreaterThan(Integer numero);
	
	
}
