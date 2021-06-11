package org.ishare.app.repositories;

import java.util.List;

import org.ishare.app.domains.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
	
	public List<Alquiler> findByCoche_Id(Long cocheId);
	
}
